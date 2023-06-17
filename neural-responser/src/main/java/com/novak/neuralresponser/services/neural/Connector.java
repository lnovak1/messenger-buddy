package com.novak.neuralresponser.services.neural;

public interface Connector {

    public void startConnection(String apiKey, String model);

    public void endConnection();
}
