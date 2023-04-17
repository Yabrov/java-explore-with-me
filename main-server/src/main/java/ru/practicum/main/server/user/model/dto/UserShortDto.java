package ru.practicum.main.server.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserShortDto {
    private Long id;
    private String name;
}
