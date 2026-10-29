package dev.frizio.lab.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.frizio.lab.dto.MessageRequestDto;
import dev.frizio.lab.service.MessageService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/messages", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> publishMessage(@Valid @RequestBody MessageRequestDto request) {
        messageService.publish(request.message());
        return ResponseEntity.accepted().build();
    }
}
