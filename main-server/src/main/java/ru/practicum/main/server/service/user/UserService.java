package ru.practicum.main.server.service.user;

import ru.practicum.main.server.dto.user.NewUserRequest;
import ru.practicum.main.server.dto.user.UserDto;

import java.util.Collection;

public interface UserService {

    Collection<UserDto> getUsers(Collection<Long> ids, Integer from, Integer size);

    UserDto createUser(NewUserRequest userRequest);

    void deleteUser(Long userId);
}
