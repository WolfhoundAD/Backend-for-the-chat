package dev.chat.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.chat.dto.MessageDTO;
import dev.chat.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create")
    public MessageDTO createMessage(@RequestParam("message") String messageContent,
                                    @RequestParam(value = "file", required = false) MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDTO messageDTO;
        try {
            messageDTO = objectMapper.readValue(messageContent, MessageDTO.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse message content", e);
        }
        // Убедитесь, что все поля инициализируются правильно
        if (messageDTO.getSenderID() == null) {
            throw new IllegalArgumentException("Sender ID must not be null");
        }
        return messageService.createMessage(messageDTO, file);
    }




    @GetMapping("/chat/{chatId}")
    public List<MessageDTO> getAllMessagesForChat(@PathVariable Long chatId) {
        return messageService.getAllMessagesForChat(chatId);
    }
}
