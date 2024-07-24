package dev.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class ProfileDTO {
    private Long profileID;
    private Long userID;
    private String fullName;
    private String photoUrl;
    private String username;  // Добавлено поле для имени пользователя
    private String role;  // Добавлено поле для роли
    private Timestamp lastLogin;  // Добавлено поле для последнего входа
}

