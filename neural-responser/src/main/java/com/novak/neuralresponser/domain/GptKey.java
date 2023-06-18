package com.novak.neuralresponser.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
public class GptKey implements Serializable {
   private final String gptKey;
}
