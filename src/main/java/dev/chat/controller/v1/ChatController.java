package dev.chat.controller.v1;

import dev.chat.dto.ChatDto;
import dev.chat.dto.UserDTO;
import dev.chat.service.ChatService;
import dev.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final UserService userService;

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers(); // метод для получения всех пользователей
    }

    @PostMapping("/create")
    public Map<String, String> createChat(@RequestBody ChatDto chatDto) {
        chatService.createChat(chatDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Chat created successfully");
        return response;
    }

    @GetMapping("/user/{userId}/chats")
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