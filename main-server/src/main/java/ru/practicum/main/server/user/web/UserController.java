package ru.practicum.main.server.user.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.server.user.model.dto.NewUserRequest;
import ru.practicum.main.server.user.model.dto.UserDto;
import ru.practicum.main.server.user.service.UserService;

import javax.validation.Valid;
import java.util.Collection;

@Validated
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/users")
    public Collection<UserDto> getUsers(
            @RequestParam(required = false) Collection<Long> ids,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @PostMapping("/admin/users")
    public UserDto createUser(@RequestBody @Valid NewUserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/admin/users/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }
}
