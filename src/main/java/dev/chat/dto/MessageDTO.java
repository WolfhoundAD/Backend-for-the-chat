package dev.chat.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class MessageDTO {
    private Long messageID;
    private Long chatID;
    private Long senderID;
    private String content;
    private LocalDate timestamp;
}
