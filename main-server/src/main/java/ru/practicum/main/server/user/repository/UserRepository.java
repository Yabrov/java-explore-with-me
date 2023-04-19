package ru.practicum.main.server.user.repository;

import org.springframework.data.domain.Pageable;
import ru.practicum.main.server.user.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(Long userId);

    User saveUser(User user);

    void deleteUser(Long userId);

    Collection<User> findAllUsers(Pageable pageable);

    Collection<User> findAllUsersByIds(Collection<Long> ids);
}
