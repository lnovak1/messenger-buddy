package com.novak.msgproducer.services;

import com.novak.msgproducer.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Log4j2
@RequiredArgsConstructor
@Service
public class StringProducerService {


    private final KafkaTemplate<String,String> kafkaTemplate;
    private final KafkaTemplate<String, Serializable> jsonTemplate;

    public void sendMessage(String message){
        log.info("sending message: {}", message);
       var future =  kafkaTemplate.send("conversation-topic",message);
//       future.whenComplete((result,exception) ->{
//          if (result != null){
//              log.info("Send message with success ! message: {},partition {}, offset{}",
//                      message,
//                      result.getRecordMetadata().partition(),
//                      result.getRecordMetadata().offset());
//          }
//          if(exception!= null){
//              log.error("Error while sending message {}", exception.getStackTrace());
//          }
//       });

    }

    public void sendJsonMessage(Message message){

        log.info("sending message: {}", message);
        var future =  jsonTemplate.send("conversation-topic",message);


    }
}
