package dev.chat.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @Column(name = "FullName")
    private String fullName;

    @Column(name = "PhotoUrl")
    private String photoUrl;


}