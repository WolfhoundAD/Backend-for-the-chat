package dev.chat.entity;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "full_name")
    private String fullName;

    //todo
    @Column(name = "photo_url")
    private String photoUrl;


}