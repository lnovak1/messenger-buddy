package com.novak.conversationservice.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConversationDto {
    private String accountSid;
    private String twilioToken;
}
