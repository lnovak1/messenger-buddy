package com.novak.wppservice.services.conectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Log4j2
@Service
public class ConversationConnectorImpl implements ConversationConnector {
    @Override
    public void startConnection(String string) {
    log.info("request to start connection");
    }

    @Override
    public void onMessage(String message) {

    }

    @Override
    public void sendMessage(String message) {

    }
}
