package dev.chat.mapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import dev.chat.dto.ChatDto;
import dev.chat.entity.Chat;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    Chat chatDTOToChat(ChatDto dto);

    @Mappings({
            @Mapping(source = "chatId", target = "chatID"),
            @Mapping(source = "chatName", target = "chatName")
    })
    ChatDto chatToChatDTO(Chat chat);
}

