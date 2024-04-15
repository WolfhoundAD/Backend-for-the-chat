package dev.chat.entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ChatParticipants")
public class ChatParticipant {

    @EmbeddedId
    private ChatParticipantId id;

    @ManyToOne
    @MapsId("chatID")
    @JoinColumn(name = "ChatID")
    private Chat chat;

    @ManyToOne
    @MapsId("profileID")
    @JoinColumn(name = "ProfileID")
    private Profile profile;
}