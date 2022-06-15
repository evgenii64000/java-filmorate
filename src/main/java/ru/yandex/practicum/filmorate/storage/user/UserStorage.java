package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;

public interface UserStorage {

    long generateId();

    User create(User user);

    User update(User user);

    void delete(User user);

    HashSet<User> getUsers();

    User getUserById(Long id);
}
