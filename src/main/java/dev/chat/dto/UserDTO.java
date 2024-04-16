package dev.chat.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {
    private Long userID;
    private String username;
    private String password;
    private String role;
    private LocalDate lastLogin;
}
