package com.novak.msgconsumer.listeners;

import com.novak.msgconsumer.custom.StrConsumerCustomListener;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class strConsumerListener {

    @StrConsumerCustomListener(groupId = "group-1")
    public void listener(String message){
        log.info("received message : {}", message);
    }

}
