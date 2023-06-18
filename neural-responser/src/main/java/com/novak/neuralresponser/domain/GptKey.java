package com.novak.neuralresponser.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class GptKey implements Serializable {
   private  String gptKey;
}
