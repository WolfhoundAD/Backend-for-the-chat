package dev.chat.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private Long profileID;
    private Long userID;
    private String fullName;
    private String photoUrl;
}

