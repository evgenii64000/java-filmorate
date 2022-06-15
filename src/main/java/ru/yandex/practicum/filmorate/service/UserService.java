package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    public UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(Long user1Id, Long user2Id) {
        User user1 = userStorage.getUserById(user1Id);
        User user2 = userStorage.getUserById(user2Id);
        if (user1.equals(user2)) {
            log.warn("Переданы одинаковые пользователи");
            throw new ValidationException("Попытка добавить в друзья самого пользователя");
        }
        log.info("Пользователь добавлен в друзья");
        user1.getFriends().add(user2.getId());
        user2.getFriends().add(user1.getId());
    }

    public void deleteFriend(Long user1Id, Long user2Id) {
        User user1 = userStorage.getUserById(user1Id);
        User user2 = userStorage.getUserById(user2Id);
        if (user1.equals(user2)) {
            log.warn("Переданы одинаковые пользователи");
            throw new ValidationException("Попытка удалить из друзей самого пользователя");
        }
        log.info("Пользователь удален из друзей");
        user1.getFriends().remove(user2.getId());
        user2.getFriends().remove(user1.getId());
    }

    public Set<User> getCommonFriends(Long user1Id, Long user2Id) {
        User user1 = userStorage.getUserById(user1Id);
        User user2 = userStorage.getUserById(user2Id);
        HashSet<Long> user2Friends = user2.getFriends();
        return user1.getFriends().stream()
                .filter(user -> user2Friends.contains(user))
                .map(id -> userStorage.getUserById(id))
                .collect(Collectors.toSet());
    }

    public Set<User> getFriendsList(Long userId) {
        return userStorage.getUserById(userId).getFriends().stream()
                .map(id -> userStorage.getUserById(id))
                .collect(Collectors.toSet());
    }
}
