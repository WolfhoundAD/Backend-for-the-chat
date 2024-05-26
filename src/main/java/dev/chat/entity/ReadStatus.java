package dev.chat.entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "read_status")
public class ReadStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readStatusId;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message messageId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profileId;

    @Column(name = "is_read")
    //todo почитать boolean vs Boolean - оставить boolean, т.к. null не нужен
    private boolean isRead;
}