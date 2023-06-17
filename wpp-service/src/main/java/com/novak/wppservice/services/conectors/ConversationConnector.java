package com.novak.wppservice.services.conectors;

public interface ConversationConnector extends Connector {
    public void onMessage(String message);

    public void sendMessage(String message);
}
