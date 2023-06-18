package com.novak.conversationservice.domain;

import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.entities.User;

import java.io.Serializable;

@Setter
@Getter
public class NeuralProcessRequest implements Serializable {
    private String userId;
    private String message;
}
