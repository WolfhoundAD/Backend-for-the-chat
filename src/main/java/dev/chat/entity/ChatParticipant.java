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
    @MapsId("chatId")
    @JoinColumn(name = "ChatID")
    private Chat chatId;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "ProfileID")
    private Profile profileId;
}