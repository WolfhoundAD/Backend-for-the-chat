package dev.chat.controller.v1;

import dev.chat.dto.MessageDTO;
import dev.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO createdMessage = messageService.createMessage(messageDTO);
        if (createdMessage != null) {
            return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<MessageDTO>> getAllMessagesForChat(@PathVariable Long chatId) {
        List<MessageDTO> messages = messageService.getAllMessagesForChat(chatId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
