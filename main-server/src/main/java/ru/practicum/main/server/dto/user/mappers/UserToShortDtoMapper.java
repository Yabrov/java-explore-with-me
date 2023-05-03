package ru.practicum.main.server.dto.user.mappers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.practicum.main.server.dto.user.UserShortDto;
import ru.practicum.main.server.model.entities.User;

@Component
public class UserToShortDtoMapper implements Converter<User, UserShortDto> {

    @Override
    public UserShortDto convert(User source) {
        return new UserShortDto(source.getId(), source.getName());
    }
}
