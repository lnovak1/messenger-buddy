package com.novak.conversationservice.conversation;

import com.novak.conversationservice.custom.NeuralConsumerCustomListener;

import com.novak.conversationservice.domain.NeuralProcessResponse;

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
public class ConversationConnectorImpl implements ConversationConnector {



    private final KafkaTemplate<String, Serializable> kafkaTemplate;



    @Override
    public void startConnection(String apiKey) {

    }

    @Override
    public void endConnection() {

    }


    @Override
    @NeuralConsumerCustomListener
    public String processBestMatch(@Payload NeuralProcessResponse neuralProcessResponse) {

//        log.info("Sending message: {}", text);
//         NeuralProcessRequest neuralProcessRequest = new NeuralProcessRequest();
//         neuralProcessResponse.setMessage(text);
//        kafkaTemplate.send("neural-conversation",neuralProcessResponse);
        log.info("RECEIVED IN CONVERSATION::::: {}",neuralProcessResponse.toString());
        return null;
    }
}
