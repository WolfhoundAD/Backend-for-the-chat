package dev.chat.service;

import dev.chat.dto.MessageDTO;
import dev.chat.entity.Attachment;
import dev.chat.entity.Message;
import dev.chat.entity.User;
import dev.chat.handler.ChatWebSocketHandler;
import dev.chat.mapper.MessageMapper;
import dev.chat.repository.ChatRepository;
import dev.chat.repository.MessageRepository;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageMapper messageMapper;
    private final ChatWebSocketHandler chatWebSocketHandler;
    private final MinioService minioService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository, ChatRepository chatRepository, MessageMapper messageMapper, ChatWebSocketHandler chatWebSocketHandler, MinioService minioService) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.messageMapper = messageMapper;
        this.chatWebSocketHandler = chatWebSocketHandler;
        this.minioService = minioService;
    }

    public MessageDTO createMessage(MessageDTO messageDTO, MultipartFile file) {
        if (messageDTO.getSenderID() == null) {
            throw new IllegalArgumentException("Sender ID must not be null");
        }

        Optional<User> senderOptional = userRepository.findById(messageDTO.getSenderID());
        if (!senderOptional.isPresent()) {
            throw new RuntimeException("Sender not found");
        }

        Message message = messageMapper.messageDTOToMessage(messageDTO);
        message.setSender(senderOptional.get());

        if (file != null && !file.isEmpty()) {
            String filePath = saveFileToMinio(file);
            Attachment attachment = Attachment.builder()
                    .filePath(filePath)
                    .message(message)
                    .build();

            // Инициализация списка вложений, если он null
            if (message.getAttachments() == null) {
                message.setAttachments(new ArrayList<>());
            }

            message.getAttachments().add(attachment);
        }

        Message savedMessage = messageRepository.save(message);
        MessageDTO messageDTOToSend = messageMapper.messageToMessageDTO(savedMessage);

        chatWebSocketHandler.sendMessageToAllClients(messageDTOToSend);

        return messageDTOToSend;
    }

    private String saveFileToMinio(MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            minioService.uploadFile(inputStream, fileName, file.getContentType());
            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file to MinIO", e);
        }
    }

    public List<MessageDTO> getAllMessagesForChat(Long chatId) {
        List<Message> messages = messageRepository.findMessagesByChatId(chatId);
        return messages.stream().map(messageMapper::messageToMessageDTO).collect(Collectors.toList());
    }
}
