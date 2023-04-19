package ru.practicum.main.server.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.main.server.user.model.User;

import java.util.Collection;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Collection<User> findAllByIdIn(Collection<Long> ids);
}
