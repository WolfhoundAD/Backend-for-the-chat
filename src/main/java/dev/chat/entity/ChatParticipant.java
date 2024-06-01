package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

//todo composite key - сделал, посмотреть
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_participants")
@IdClass(ChatParticipant.ChatParticipantId.class)
public class ChatParticipant {

    @Id
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Вложенный класс для представления композиционного ключа
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class ChatParticipantId implements Serializable {
        private Long chat;
        private Long user;
    }
}