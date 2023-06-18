package com.novak.conversationservice.services.conversation;

import com.novak.conversationservice.domain.NeuralProcessResponse;

public interface ConversationConnector extends Connector{

    public String consumeBestMatch(NeuralProcessResponse neuralProcessRequest);
}
