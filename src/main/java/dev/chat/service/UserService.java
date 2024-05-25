package dev.chat.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.chat.dto.UserDTO;
import dev.chat.entity.User;
import dev.chat.repository.UserRepository;
import dev.chat.mapper.UserMapper;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

