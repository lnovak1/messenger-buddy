package com.novak.conversationservice.conversation;

import com.novak.conversationservice.domain.NeuralProcessResponse;

public interface ConversationConnector extends Connector{

    public String processBestMatch(NeuralProcessResponse neuralProcessRequest);
}
