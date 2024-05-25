package dev.chat.mapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import dev.chat.dto.ProfileDTO;
import dev.chat.entity.Profile;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProfileMapper {
    ProfileMapper INSTANCE = Mappers.getMapper(ProfileMapper.class);

    @Mappings({
            @Mapping(source = "userID", target = "user.id")
    })
    Profile profileDTOToProfile(ProfileDTO profileDTO);

    @Mappings({
            @Mapping(source = "profileId", target = "profileID"),
            @Mapping(source = "user.id", target = "userID"),
            @Mapping(source = "fullName", target = "fullName"),
            @Mapping(source = "photoUrl", target = "photoUrl")
    })
    ProfileDTO profileToProfileDTO(Profile profile);
}

