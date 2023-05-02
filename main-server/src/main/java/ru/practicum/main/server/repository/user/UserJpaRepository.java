package ru.practicum.main.server.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.User;

import java.util.Collection;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    Collection<User> findAllByIdIn(Collection<Long> ids);
}
