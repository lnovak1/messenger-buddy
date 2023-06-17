package com.novak.wppservice.controllers;


import com.novak.wppservice.controllers.dtos.StartConnectionDto;
import com.novak.wppservice.services.conectors.ConversationConnector;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/startConnection")
public class ConversationConnectorController {

    private final ConversationConnector conversationConnector;

    @PostMapping
    public ResponseEntity startConnection(@RequestBody StartConnectionDto startConnectionDto){
        conversationConnector.startConnection(startConnectionDto.getStart());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
