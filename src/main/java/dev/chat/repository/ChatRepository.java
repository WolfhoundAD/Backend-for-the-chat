package dev.chat.repository;

import dev.chat.entity.Chat;
import dev.chat.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
 //   List<Chat> findByParticipants(@Param("profile") Profile profile);
}
