package dev.chat.entity;
import lombok.*;
import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;

    @Column(name = "last_login")
    //todo datetime&
    private LocalDate lastLogin;
}