package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
//todo data?
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "ChatID")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "SenderID")
    private Profile sender;

    @Column(name = "Content")
    private String content;

    @Column(name = "Timestamp")
    private LocalDate timestamp;

    @OneToMany(mappedBy = "message")
    private List<Attachment> attachments;
}