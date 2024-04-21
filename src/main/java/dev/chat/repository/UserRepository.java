package dev.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.chat.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //List<User> findByUserId(Long UserId);
}