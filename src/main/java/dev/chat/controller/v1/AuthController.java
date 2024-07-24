package dev.chat.controller.v1;

import dev.chat.dto.ProfileDTO;
import dev.chat.dto.UserDTO;
import dev.chat.entity.User;
import dev.chat.service.CustomUserDetailsService;
import dev.chat.service.ProfileService;
import dev.chat.service.UserService;
import dev.chat.util.MinioUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@Slf4j
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestParam("username") String username,
                                            @RequestParam("password") String password,
                                            @RequestParam("fullName") String fullName,
                                            @RequestParam("photo") MultipartFile photoFile) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(passwordEncoder.encode(password));

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setFullName(fullName);

        try {
            userService.registerUser(userDTO, profileDTO, photoFile);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to register user", e);
        }

        Map<String, String> response = new HashMap<>();
        response.put("message", "User and profile registered successfully");
        return response;
    }


    @PostMapping("/login")
    public ProfileDTO apiLogin(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDTO.getUsername());

        if (passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            User user = userService.findByUsername(userDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            ProfileDTO profileDTO = profileService.getProfileByUserId(user.getId());
            if (profileDTO == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
            }

            profileDTO.setUsername(user.getUsername());
            profileDTO.setRole(user.getRole());
            profileDTO.setLastLogin(user.getLastLogin());
            // Генерация полного URL для photoUrl
            String fullPhotoUrl = MinioUrlGenerator.generateMinioUrl(profileDTO.getPhotoUrl());
            profileDTO.setPhotoUrl(fullPhotoUrl);

            return profileDTO;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }


    @PostMapping(path = "/logout", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Principal logout(Principal user, HttpServletRequest request, HttpServletResponse response) {

        CookieClearingLogoutHandler cookieClearingLogoutHandler = new CookieClearingLogoutHandler(
                AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY
        );
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);

        return user;
    }

}