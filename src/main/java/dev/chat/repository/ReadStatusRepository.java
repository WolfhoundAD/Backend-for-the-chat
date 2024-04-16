package dev.chat.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.chat.entity.ReadStatus;

@Repository
public interface ReadStatusRepository extends JpaRepository<ReadStatus, Long> {
}
