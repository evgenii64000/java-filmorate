package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    public FilmStorage filmStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long userId, Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        if (film.getUserLike().add(userId)) {
            log.info("Добавлен like");
            film.setLikeCount(film.getLikeCount() + 1);
        } else {
            log.warn("Повторное добавление like");
            throw new ValidationException("Попытка дублировать like");
        }
    }

    public void deleteLike(Long userId, Long filmId) {
        Film film = filmStorage.getFilmById(filmId);
        if (userId < 0) {
            log.warn("Пользователь с таким id не найден");
            throw new NotFoundException("Пользователь с таким id не найден");
        }
        if (film.getUserLike().remove(userId)) {
            log.info("Удален like");
            film.setLikeCount(film.getLikeCount() - 1);
        } else {
            log.warn("Удаление несуществующего like");
            throw new ValidationException("Данный пользователь не ставил like");
        }
    }

    public List<Film> getTop(Integer count) {
        return filmStorage.getFilms().stream()
                .sorted((f1, f2) -> -f1.getLikeCount().compareTo(f2.getLikeCount()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
