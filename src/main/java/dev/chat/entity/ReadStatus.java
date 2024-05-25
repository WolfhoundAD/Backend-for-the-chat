package dev.chat.entity;
import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ReadStatus")
public class ReadStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long readStatusId;

    @ManyToOne
    @JoinColumn(name = "MessageID")
    private Message messageId;

    @ManyToOne
    @JoinColumn(name = "ProfileID")
    private Profile profileId;

    @Column(name = "IsRead")
    //todo почитать boolean vs Boolean
    private boolean isRead;
}