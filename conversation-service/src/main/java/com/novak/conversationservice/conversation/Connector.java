package com.novak.conversationservice.conversation;

public interface Connector {

    public void startConnection(String apiKey);

    public void endConnection();
}
