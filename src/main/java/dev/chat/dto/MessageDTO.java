package dev.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MessageDTO {
    private Long messageID;
    private Long chatID;
    private Long senderID;
    private String content;
    private Timestamp timestamp;
    private List<AttachmentDTO> attachments = new ArrayList<>();
}
