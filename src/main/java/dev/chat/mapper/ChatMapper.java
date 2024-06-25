package dev.chat.mapper;
import dev.chat.dto.ChatDto;
import dev.chat.entity.Chat;
import dev.chat.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = {User.class, Collectors.class})
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mappings({
            @Mapping(target = "participants", ignore = true)
    })
    Chat chatDTOToChat(ChatDto dto);

    @Mappings({
            @Mapping(source = "chatId", target = "chatID"),
            @Mapping(source = "chatName", target = "chatName"),
            @Mapping(target = "participantIds", expression = "java(chat.getParticipants().stream().map(User::getId).collect(Collectors.toList()))")
    })
    ChatDto chatToChatDTO(Chat chat);
}

