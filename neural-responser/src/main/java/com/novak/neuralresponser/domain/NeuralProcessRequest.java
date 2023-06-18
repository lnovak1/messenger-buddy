package com.novak.neuralresponser.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class NeuralProcessRequest implements Serializable {
    private String myNumber;
    private String toNumber;
    private String message;
}
