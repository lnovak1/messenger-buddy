package com.novak.neuralresponser.services.neural;

import com.novak.neuralresponser.domain.GptKey;
import org.springframework.messaging.handler.annotation.Payload;

public interface Connector {

    public void startConnection(@Payload GptKey gptKey);

    public void endConnection();
}
