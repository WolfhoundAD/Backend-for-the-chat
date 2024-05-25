package dev.chat.controller.v1;

import dev.chat.dto.ChatDto;
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

    public ChatDto createChat(@RequestParam String chatName, @RequestParam List<Long> participantIds) {
        return chatService.createChat(chatName, participantIds);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ChatDto>> getAllChatsForUser(@PathVariable Long userId) {
        List<ChatDto> chatDtos = chatService.getAllChatsForUser(userId);
        return new ResponseEntity<>(chatDtos, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/rename")
    public ResponseEntity<ChatDto> renameChat(@PathVariable Long chatId, @RequestParam String newChatName) {
        ChatDto chatDto = chatService.renameChat(chatId, newChatName);
        if (chatDto != null) {
            return new ResponseEntity<>(chatDto, HttpStatus.OK);
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
