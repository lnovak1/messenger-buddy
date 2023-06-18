package com.novak.conversationservice.domain;

import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public class GptKey implements Serializable {
   private final String gptKey;
}
