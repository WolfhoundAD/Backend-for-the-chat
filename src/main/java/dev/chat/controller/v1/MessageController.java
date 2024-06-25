package dev.chat.controller.v1;

import dev.chat.dto.MessageDTO;
import dev.chat.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create")
    public MessageDTO createMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.createMessage(messageDTO);
    }

    @GetMapping("/chat/{chatId}")
    public List<MessageDTO> getAllMessagesForChat(@PathVariable Long chatId) {
        return messageService.getAllMessagesForChat(chatId);
    }
}
