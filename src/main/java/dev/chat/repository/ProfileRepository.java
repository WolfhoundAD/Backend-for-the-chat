package dev.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.chat.entity.Profile;

import java.util.List;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByUserId(Long userId);
}

