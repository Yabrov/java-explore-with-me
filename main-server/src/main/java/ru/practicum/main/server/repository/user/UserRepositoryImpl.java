package ru.practicum.main.server.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.main.server.model.entities.User;

import java.util.Collection;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> getUserById(Long userId) {
        return jpaRepository.findById(userId);
    }

    @Override
    public User saveUser(User user) {
        return jpaRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        jpaRepository.deleteById(userId);
    }

    @Override
    public Collection<User> findAllUsers(Pageable pageable) {
        return jpaRepository.findAll(pageable).getContent();
    }

    @Override
    public Collection<User> findAllUsersByIds(Collection<Long> ids) {
        return jpaRepository.findAllByIdIn(ids);
    }
}
