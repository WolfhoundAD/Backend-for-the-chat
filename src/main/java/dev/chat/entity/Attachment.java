package dev.chat.entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Attachments")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attachmentID;

    @ManyToOne
    @JoinColumn(name = "MessageID")
    private Message message;

    @Column(name = "FilePath")
    private String filePath;
}