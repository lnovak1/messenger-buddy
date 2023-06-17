package com.novak.neuralresponser.services.neural;

import com.novak.neuralresponser.domain.NeuralProcessRequest;

public interface NeuralConnector extends Connector{

    public String processBestMatch(NeuralProcessRequest neuralProcessRequest);
}
