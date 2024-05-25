package dev.chat.mapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import dev.chat.dto.UserDTO;
import dev.chat.entity.User;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDTOToUser(UserDTO dto);

    @Mappings({
            @Mapping(source = "id", target = "userID"),
            @Mapping(source = "username", target = "username"),
            @Mapping(source = "password", target = "password"),
            @Mapping(source = "role", target = "role"),
            @Mapping(source = "lastLogin", target = "lastLogin")
    })
    UserDTO userToUserDTO(User user);
}
