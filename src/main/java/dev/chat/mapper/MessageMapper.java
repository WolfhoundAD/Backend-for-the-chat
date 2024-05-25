package dev.chat.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import dev.chat.dto.MessageDTO;
import dev.chat.entity.Message;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message messageDTOToMessage(MessageDTO dto);

    @Mappings({
            @Mapping(source = "messageId", target = "messageID"),
            @Mapping(source = "chat.chatId", target = "chatID"),
            @Mapping(source = "sender.profileId", target = "senderID"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "timestamp", target = "timestamp")
    })
    MessageDTO messageToMessageDTO(Message message);
}

