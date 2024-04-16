package dev.chat.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDTO {
    private Long attachmentID;
    private Long messageID;
    private String filePath;
}
