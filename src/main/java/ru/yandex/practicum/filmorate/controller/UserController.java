package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends Controller<User> {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PostMapping
    public User post(@RequestBody @Valid User user) {
        return userService.userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        return userService.userStorage.update(user);
    }

    @GetMapping
    public HashSet<User> users() {
        return userService.userStorage.getUsers();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.userStorage.getUserById(id);
    }

    @GetMapping("/{id}/friends")
    public Set<User> getFriendsList(@PathVariable Long id) {
        return userService.getFriendsList(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id,
                          @PathVariable Long friendId) {
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long id,
                             @PathVariable Long friendId) {
        userService.deleteFriend(id, friendId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Set<User> getCommonFriends(@PathVariable Long id,
                                      @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }
}
