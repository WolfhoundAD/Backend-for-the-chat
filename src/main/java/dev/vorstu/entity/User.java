package dev.vorstu.entity;

import dev.vorstu.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch =  FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id", nullable = false)
    private Password password;

    private boolean enable;
}
