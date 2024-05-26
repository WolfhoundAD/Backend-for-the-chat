package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Profile sender;

    @Column(name = "content")
    private String content;

    @Column(name = "timestamp")
    private LocalDate timestamp;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments;
}