package com.novak.conversationservice.conversation;

import com.novak.conversationservice.custom.NeuralConsumerCustomListener;
import com.novak.conversationservice.domain.NeuralProcessResponse;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
//import com.twilio.twiml.MessagingResponse;
//import com.twilio.twiml.message.Body;
//import com.twilio.twiml.message.Message;
//import spark.Spark;

import java.io.Serializable;

@Log4j2
@Service
@RequiredArgsConstructor
public class ConversationConnectorImpl implements ConversationConnector {


    private final KafkaTemplate<String, Serializable> kafkaTemplate;


    @Override
    public void startConnection(String accountSid, String twilioToken) {
        Twilio.init(accountSid, twilioToken);
    }

    @Override
    public void endConnection() {

    }

    public void sendTwilioMessage(String messageText) {

        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+5521967174448"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        messageText)

                .create();
        Message message2 = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:+5521974928225"),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        messageText)

                .create();

    }

//    @Bean
//    public void messsagerListener(){
//        Spark.post("/webhook", (request, response) -> {
//            String messageBody = request.queryParams("Body");
//            String senderNumber = request.queryParams("From");
//    }



    @Override
    @NeuralConsumerCustomListener
    public String processBestMatch(@Payload NeuralProcessResponse neuralProcessResponse) {
        log.info("RECEIVED IN CONVERSATION::::: {}",neuralProcessResponse.getMessage());
        sendTwilioMessage(neuralProcessResponse.getMessage());
//        log.info("Sending message: {}", text);
//         NeuralProcessRequest neuralProcessRequest = new NeuralProcessRequest();
//         neuralProcessResponse.setMessage(text);
//        kafkaTemplate.send("neural-conversation",neuralProcessResponse);

        return null;
    }
}
