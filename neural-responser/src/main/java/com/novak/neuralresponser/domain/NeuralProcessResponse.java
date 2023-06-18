package com.novak.neuralresponser.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class NeuralProcessResponse implements Serializable {
    private final String userId;
    private final String message;
}
