package ru.practicum.main.server.user.model.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.user.model.User;
import ru.practicum.main.server.user.model.dto.UserDto;

@Component
public class UserMapper implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getId(), source.getName(), source.getEmail());
    }
}
