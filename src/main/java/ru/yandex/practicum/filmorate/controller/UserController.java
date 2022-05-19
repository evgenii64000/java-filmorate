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
public class UserController extends Controller<User> {

    @Override
    @PostMapping
    public User post(@RequestBody @Valid User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пароль содержит пробелы");
            throw new ValidationException("Пароль не должен содержать пробелов");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        log.info("Создан новый пользователь");
        return super.post(user);
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
        log.info("Информация о пользователе обновлена");
        return super.update(user);
    }

    @GetMapping
    public HashSet<User> users() {
        return super.returnList();
    }
}
