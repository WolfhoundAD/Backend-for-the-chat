package dev.vorstu.config;
import dev.vorstu.enums.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/login/**").permitAll()
                // For STUDENT
                .antMatchers("/api/base/students/**").hasAuthority(Role.STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                                         AuthenticationException authException) {
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                })
                .and()
                .csrf().disable()
                .cors().disable();

        return http.build();
    }

    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder)
                .usersByUsernameQuery(
                        "select username, p.password as password, enable "
                                + "from users as u "
                                + "inner join password as p on u.password_id = p.id "
                                + "where username = ?")
                .authoritiesByUsernameQuery("select username, role from users where username = ?");
    }
}