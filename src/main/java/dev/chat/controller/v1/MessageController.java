package dev.chat.controller.v1;

import dev.chat.entity.Message;
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
    public ResponseEntity<Message> createMessage(@RequestParam Long chatId, @RequestParam Long senderId, @RequestParam String content) {
        Message message = messageService.createMessage(chatId, senderId, content);
        if (message != null) {
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getAllMessagesForChat(@PathVariable Long chatId) {
        List<Message> messages = messageService.getAllMessagesForChat(chatId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
