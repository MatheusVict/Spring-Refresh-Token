package dev.matheusvictor.refreshtoken.repository;

import dev.matheusvictor.refreshtoken.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
