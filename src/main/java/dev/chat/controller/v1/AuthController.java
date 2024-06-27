package dev.chat.controller.v1;

import dev.chat.dto.UserDTO;
import dev.chat.entity.User;
import dev.chat.service.CustomUserDetailsService;
import dev.chat.service.UserService;
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
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.createUser(userDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        return response;
    }

    @PostMapping("/login")
    public UserDTO apiLogin(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userDTO.getUsername());

        if (passwordEncoder.matches(userDTO.getPassword(), userDetails.getPassword())) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            User user = userService.findByUsername(userDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            UserDTO responseUserDTO = new UserDTO();
            responseUserDTO.setUserID(user.getId());
            responseUserDTO.setUsername(user.getUsername());
            responseUserDTO.setRole(user.getRole());
            responseUserDTO.setLastLogin(user.getLastLogin()); //todo добавить маппер

            return responseUserDTO;
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