package ru.practicum.main.server.dto.user.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.model.entities.User;
import ru.practicum.main.server.dto.user.UserDto;

@Component
public class UserMapper implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getId(), source.getName(), source.getEmail());
    }
}
