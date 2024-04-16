package dev.chat.controller.v1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.chat.entity.Message;
import dev.chat.repository.MessageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<List<Message>> getMessagesByChatId(@PathVariable Long chatId) {
        List<Message> messages = messageRepository.findByChat_ChatId(chatId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageRepository.save(message);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }
}