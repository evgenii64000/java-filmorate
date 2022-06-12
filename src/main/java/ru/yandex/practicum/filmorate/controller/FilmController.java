package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends Controller<Film> {

    @Override
    @PostMapping
    public Film post(@RequestBody @Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        film.setId(super.generateId());
        log.info("Добавлен фильм");
        return super.post(film);
    }

    @Override
    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        if (film.getId() <= 0) {
            log.warn("Неверный ID фильма");
            throw new ValidationException("Неверный ID фильма");
        }
        log.info("Фильм обновлён");
        return super.update(film);
    }

    @GetMapping
    public HashSet<Film> films() {
        return super.returnList();
    }
}
