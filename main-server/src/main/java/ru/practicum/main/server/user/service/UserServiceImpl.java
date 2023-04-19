package ru.practicum.main.server.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.server.config.PageBuilder;
import ru.practicum.main.server.user.exception.UserNotFoundException;
import ru.practicum.main.server.user.exception.UserViolationException;
import ru.practicum.main.server.user.model.User;
import ru.practicum.main.server.user.model.dto.NewUserRequest;
import ru.practicum.main.server.user.model.dto.UserDto;
import ru.practicum.main.server.user.repository.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Converter<NewUserRequest, User> userRequestMapper;
    private final Converter<User, UserDto> userMapper;
    private final PageBuilder pageBuilder;

    @Transactional(readOnly = true)
    @Override
    public Collection<UserDto> getUsers(Collection<Long> ids, Integer from, Integer size) {
        return ids == null || ids.isEmpty()
                ? userRepository
                .findAllUsers(pageBuilder.build(from, size, null))
                .stream()
                .map(userMapper::convert)
                .collect(Collectors.toList())
                : userRepository
                .findAllUsersByIds(ids)
                .stream()
                .map(userMapper::convert)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto createUser(NewUserRequest userRequest) {
        try {
            User user = userRequestMapper.convert(userRequest);
            return userMapper.convert(userRepository.saveUser(user));
        } catch (Exception e) {
            throw new UserViolationException(e);
        }
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        userRepository.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        userRepository.deleteUser(userId);
    }
}
