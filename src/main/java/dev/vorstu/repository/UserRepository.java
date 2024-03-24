package dev.vorstu.repository;

import dev.vorstu.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    // Custom query methods can be declared here if needed
}
