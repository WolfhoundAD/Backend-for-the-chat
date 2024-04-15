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
    private Long readStatusID;

    @ManyToOne
    @JoinColumn(name = "MessageID")
    private Message message;

    @ManyToOne
    @JoinColumn(name = "ProfileID")
    private Profile profile;

    @Column(name = "IsRead")
    private boolean isRead;
}