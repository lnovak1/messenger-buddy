package com.novak.neuralresponser.services.neural;

import com.novak.neuralresponser.custom.ConversationConsumerCustomListener;
import com.novak.neuralresponser.custom.GptKeyUpdateConsumerCustomListener;
import com.novak.neuralresponser.domain.GptKey;
import com.novak.neuralresponser.domain.NeuralProcessRequest;
import com.novak.neuralresponser.domain.NeuralProcessResponse;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;

@Log4j2
@Service
@RequiredArgsConstructor
public class NeuralConnectorImpl implements NeuralConnector {

    private OpenAiService openAiService;


    private final KafkaTemplate<String, Serializable> kafkaTemplate;


    @Override
    @GptKeyUpdateConsumerCustomListener
    public void startConnection(@Payload GptKey gptKey) {
        if (openAiService != null) {
            endConnection();
        }

        openAiService = new OpenAiService(gptKey.getGptKey(), Duration.ofDays(7L));
        log.info("OPEN AI STARTED WITH KEY :{}", gptKey.getGptKey());
    }

    @Override
    public void endConnection() {
        openAiService.shutdownExecutor();
    }


    @Override
    @ConversationConsumerCustomListener
    public String processBestMatch(@Payload NeuralProcessRequest neuralProcessRequest) {
        if (openAiService != null) {


            CompletionRequest completionRequest = CompletionRequest.builder()
                    .prompt("preciso que sua proxima resposta seja tentando se passar por mim , essa é a mensagem que recebi: " + neuralProcessRequest.getMessage())
                    .model("text-davinci-003")
//                .n(1)
                    .temperature(0.5D)
                    .frequencyPenalty(0.5D)
                    .bestOf(1)
                    .presencePenalty(0.3D)
                    .maxTokens(1000)
                    .logitBias(new HashMap<>())
                    .build();
            CompletionResult completion = openAiService.createCompletion(completionRequest);

            String text = completion.getChoices().get(0).getText();
            log.info("Sending message: {}", text);
            NeuralProcessResponse neuralProcessResponse = new NeuralProcessResponse(neuralProcessRequest.getUserId(),text);
            kafkaTemplate.send("neural-topic", neuralProcessResponse);
            return text;
        }else{
            String errorMsg = "Open Ai service is offline , maybe you need to setup my GPTAPI key";
            kafkaTemplate.send("neural-topic", new NeuralProcessResponse(neuralProcessRequest.getUserId(),errorMsg));
            return errorMsg;
        }

    }
}
