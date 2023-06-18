package com.novak.conversationservice.conversation;

public interface Connector {

    public void startConnection(String accountSid, String twilioToken);

    public void endConnection();
}
