package com.novak.neuralresponser.services.neural;

import com.novak.neuralresponser.domain.NeuralProcessRequest;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class NeuralConnectorImpl implements NeuralConnector{

    private OpenAiService openAiService;

    private String model;



    @Override
    public void startConnection(String apiKey, String model) {
        openAiService =  new OpenAiService(apiKey, Duration.ofDays(7L));
        this.model = model;
    }

    @Override
    public void endConnection() {
        openAiService.shutdownExecutor();
    }

    @Override
    public String processBestMatch(NeuralProcessRequest neuralProcessRequest) {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(neuralProcessRequest.getMessage())
                .model(this.model)
                .echo(true)
                .build();
        CompletionResult completion = openAiService.createCompletion(completionRequest);
        return completion.getChoices().get(0).getText();
    }
}
