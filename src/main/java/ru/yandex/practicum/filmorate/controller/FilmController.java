package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final HashSet<Film> films = new HashSet<Film>();
    private long idCounter = 0;

    private long generateId() {
        idCounter++;
        return idCounter;
    }

    @PostMapping
    public Film post(@RequestBody @Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        film.setId(generateId());
        films.add(film);
        log.info("Добавлен фильм");
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        films.remove(film);
        log.info("Фильм обновлён");
        films.add(film);
        return film;
    }

    @GetMapping
    public HashSet<Film> films() {
        return films;
    }
}
