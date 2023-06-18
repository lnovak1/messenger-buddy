package com.novak.msgproducer.domain;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class Message implements Serializable {

    private String message;
}
