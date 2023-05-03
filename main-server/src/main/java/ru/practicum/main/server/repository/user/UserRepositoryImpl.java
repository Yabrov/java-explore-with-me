package ru.practicum.main.server.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main.server.model.entities.User;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> getUserById(Long userId) {
        return userJpaRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userJpaRepository.deleteById(userId);
    }

    @Override
    public Collection<User> findAllUsers(Pageable pageable) {
        return userJpaRepository.findAll(pageable).getContent();
    }

    @Override
    public Collection<User> findAllUsersByIds(Collection<Long> ids) {
        return userJpaRepository.findAllByIdIn(ids);
    }
}
