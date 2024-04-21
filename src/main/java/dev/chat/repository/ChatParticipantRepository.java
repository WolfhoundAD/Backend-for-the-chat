package dev.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.chat.entity.ChatParticipant;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
}
