package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @Column(name = "chat_name")
    private String chatName;

    @ManyToMany //можно ли сделать сохранение без сущность, чисто по айди
    @JoinTable(
            name = "chat_participants",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants;
}