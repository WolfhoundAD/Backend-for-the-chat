package dev.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.chat.entity.ChatParticipant;
import dev.chat.entity.ChatParticipantId;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, ChatParticipantId> {
}
