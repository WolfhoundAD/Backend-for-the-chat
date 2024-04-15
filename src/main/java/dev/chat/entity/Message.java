package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageID;

    @ManyToOne
    @JoinColumn(name = "ChatID")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "SenderID")
    private Profile sender;

    @Column(name = "Content")
    private String content;

    @Column(name = "Timestamp")
    private Timestamp timestamp;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments;
}