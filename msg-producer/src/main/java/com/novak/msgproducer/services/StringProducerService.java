package com.novak.msgproducer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class StringProducerService {


    private final KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String message){
        log.info("sending message: {}", message);
       var future =  kafkaTemplate.send("str-topic",message);
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
}
