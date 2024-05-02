package dev.chat.service;

import dev.chat.dto.UserDTO;
import dev.chat.entity.User;
import dev.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = convertToUser(userDTO);
        user = userRepository.save(user);
        return convertToUserDTO(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    private User convertToUser(UserDTO userDTO) {
        return User.builder()
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .lastLogin(userDTO.getLastLogin())
                .build();
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserID(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setLastLogin(user.getLastLogin());
        return userDTO;
    }
}
