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
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Role")
    private String role;

    @Column(name = "LastLogin")
    private LocalDate lastLogin;
}