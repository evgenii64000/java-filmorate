package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final HashSet<User> users = new HashSet<User>();
    private long idCounter = 0;

    @Override
    public long generateId() {
        idCounter++;
        return idCounter;
    }

    @Override
    public User create(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пароль содержит пробелы");
            throw new ValidationException("Пароль не должен содержать пробелов");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(generateId());
        log.info("Создан новый пользователь");
        users.add(user);
        return user;
    }

    @Override
    public User update(User user) {
        if (user.getLogin().contains(" ")) {
            log.warn("Пароль содержит пробелы");
            throw new ValidationException("Пароль не должен содержать пробелов");
        }
        if (user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        if (!users.contains(user)) {
            log.warn("Пользователь с таким id не найден");
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        log.info("Информация о пользователе обновлена");
        users.remove(user);
        users.add(user);
        return user;
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }

    @Override
    public HashSet<User> getUsers() {
        return users;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> optionalUser = users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
        if (!optionalUser.isPresent()) {
            log.warn("Пользователь с таким id не найден");
            throw new NotFoundException("Пользователь с таким id не найден");
        } else {
            return optionalUser.get();
        }
    }

}
