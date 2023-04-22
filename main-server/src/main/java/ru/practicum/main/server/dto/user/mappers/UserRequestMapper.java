package ru.practicum.main.server.dto.user.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.model.entities.User;
import ru.practicum.main.server.dto.user.NewUserRequest;

@Component
public class UserRequestMapper implements Converter<NewUserRequest, User> {

    @Override
    public User convert(NewUserRequest source) {
        return new User(null, source.getName(), source.getEmail());
    }
}
