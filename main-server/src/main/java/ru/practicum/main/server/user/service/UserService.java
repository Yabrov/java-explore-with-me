package ru.practicum.main.server.user.service;

import ru.practicum.main.server.user.model.dto.NewUserRequest;
import ru.practicum.main.server.user.model.dto.UserDto;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getUsers(Collection<Long> ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest userRequest);

    void deleteUser(Long userId);
}
