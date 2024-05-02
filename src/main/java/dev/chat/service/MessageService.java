package dev.chat.service;

import dev.chat.dto.MessageDTO;
import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import dev.chat.repository.MessageRepository;
import dev.chat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ProfileRepository profileRepository) {
        this.messageRepository = messageRepository;
        this.profileRepository = profileRepository;
    }

    // Создать сообщение в чате
    public MessageDTO createMessage(MessageDTO messageDTO) {
        Optional<Profile> senderOptional = profileRepository.findById(messageDTO.getSenderID());
        if (!senderOptional.isPresent()) {
            return null;
        }

        Chat chat = Chat.builder().chatId(messageDTO.getChatID()).build();

        dev.chat.entity.Message message = dev.chat.entity.Message.builder()
                .chat(chat)
                .sender(senderOptional.get())
                .content(messageDTO.getContent())
                .timestamp(LocalDate.now())
                .build();

        dev.chat.entity.Message savedMessage = messageRepository.save(message);

        return convertToMessageDTO(savedMessage);
    }

    // Получить все сообщения для заданного чата
    public List<MessageDTO> getAllMessagesForChat(Long chatId) {
        List<dev.chat.entity.Message> messages = messageRepository.findAllByChat_ChatIdOrderByTimestamp(chatId);
        return messages.stream().map(this::convertToMessageDTO).collect(Collectors.toList());
    }

    // Преобразовать сущность Message в DTO
    private MessageDTO convertToMessageDTO(dev.chat.entity.Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageID(message.getMessageId());
        messageDTO.setChatID(message.getChat().getChatId());
        messageDTO.setSenderID(message.getSender().getProfileId());
        messageDTO.setContent(message.getContent());
        messageDTO.setTimestamp(message.getTimestamp());
        return messageDTO;
    }
}
