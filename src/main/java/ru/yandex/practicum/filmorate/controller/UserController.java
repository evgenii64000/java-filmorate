package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashSet;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final HashSet<User> users = new HashSet<User>();
    private long idCounter = 0;

    private long generateId() {
        idCounter++;
        return idCounter;
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пароль содержит пробелы");
            throw new ValidationException("Пароль не должен содержать пробелов");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        users.add(user);
        log.info("Создан новый пользователь");
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пароль содержит пробелы");
            throw new ValidationException("Пароль не должен содержать пробелов");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        users.remove(user);
        users.add(user);
        log.info("Информация о пользователе обновлена");
        return user;
    }

    @GetMapping
    public HashSet<User> users() {
        return users;
    }
}
