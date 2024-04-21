package dev.chat.service;

import dev.chat.entity.Chat;
import dev.chat.entity.Message;
import dev.chat.entity.Profile;
import dev.chat.repository.MessageRepository;
import dev.chat.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public Message createMessage(Long chatId, Long senderId, String content) {
        Optional<Profile> senderOptional = profileRepository.findById(senderId);
        if (!senderOptional.isPresent()) {
            return null;
        }

        Message message = Message.builder()
                .chat(Chat.builder().chatId(chatId).build())
                .sender(senderOptional.get())
                .content(content)
                .timestamp(LocalDate.now())
                .build();

        return messageRepository.save(message);
    }

    // Получить все сообщения для заданного чата
    public List<Message> getAllMessagesForChat(Long chatId) {
        return messageRepository.findAllByChat_ChatIdOrderByTimestamp(chatId);
    }

    // Другие возможные функции, например:
    // - Получить последнее сообщение в чате
    // - Удалить сообщение
    // - Изменить текст сообщения
    // - Получить сообщения отправленные пользователем
    // - И т.д.
}