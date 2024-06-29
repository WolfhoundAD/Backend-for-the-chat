package dev.chat.service;
import dev.chat.dto.ProfileDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import dev.chat.dto.UserDTO;
import dev.chat.entity.User;
import dev.chat.repository.UserRepository;
import dev.chat.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileService profileService;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, ProfileService profileService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.profileService = profileService;
    }

    @Transactional
    public void registerUser(UserDTO userDTO, ProfileDTO profileDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);

        profileDTO.setUserID(user.getId());
        profileService.createProfileWithoutPhoto(profileDTO);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user = userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

