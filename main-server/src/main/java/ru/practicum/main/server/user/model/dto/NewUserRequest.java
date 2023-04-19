package ru.practicum.main.server.user.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class NewUserRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty(required = true)
    private String name;

    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    @JsonProperty(required = true)
    private String email;
}
