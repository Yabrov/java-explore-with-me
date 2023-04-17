package ru.practicum.main.server.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewUserRequest {
    private String name;
    private String email;
}
