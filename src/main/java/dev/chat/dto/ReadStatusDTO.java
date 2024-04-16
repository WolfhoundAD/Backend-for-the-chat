package dev.chat.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadStatusDTO {
    private Long readStatusID;
    private Long messageID;
    private Long profileID;
    private boolean isRead;
}

