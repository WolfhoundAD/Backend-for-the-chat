package dev.chat.controller.v1;

import dev.chat.entity.Chat;
import dev.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public ResponseEntity<Chat> createChat(@RequestParam String chatName, @RequestParam List<Long> participantIds) {
        Chat chat = chatService.createChat(chatName, participantIds);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Chat>> getAllChatsForUser(@PathVariable Long userId) {
        List<Chat> chats = chatService.getAllChatsForUser(userId);
        return new ResponseEntity<>(chats, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/rename")
    public ResponseEntity<Chat> renameChat(@PathVariable Long chatId, @RequestParam String newChatName) {
        Chat chat = chatService.renameChat(chatId, newChatName);
        if (chat != null) {
            return new ResponseEntity<>(chat, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<Void> deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
