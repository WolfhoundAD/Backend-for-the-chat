package dev.chat.controller.v1;

import dev.chat.dto.ChatDto;
import dev.chat.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/create")
    public ChatDto createChat(@RequestParam String chatName, @RequestParam List<Long> participantIds) {
        return chatService.createChat(chatName, participantIds);
    }

    @GetMapping("/user/{userId}")
    public List<ChatDto> getAllChatsForUser(@PathVariable Long userId) {
        return chatService.getAllChatsForUser(userId);
    }

    @PutMapping("/{chatId}/rename")
    public ChatDto renameChat(@PathVariable Long chatId, @RequestParam String newChatName) {
        return chatService.renameChat(chatId, newChatName);
    }

    @DeleteMapping("/{chatId}")
    public void deleteChat(@PathVariable Long chatId) {
        chatService.deleteChat(chatId);
    }
}