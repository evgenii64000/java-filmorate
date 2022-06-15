package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends Controller<Film> {

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    @PostMapping
    public Film post(@RequestBody @Valid Film film) {
        return filmService.filmStorage.create(film);
    }

    @Override
    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        return filmService.filmStorage.update(film);
    }

    @GetMapping
    public HashSet<Film> films() {
        return filmService.filmStorage.getFilms();
    }

    @GetMapping("/popular")
    public List<Film> topFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.getTop(count);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id,
                        @PathVariable Long userId) {
        filmService.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id,
                           @PathVariable Long userId) {
        filmService.deleteLike(userId, id);
    }

    @GetMapping("/{id}")
    public void getFilmById(@PathVariable Long id) {
        filmService.filmStorage.getFilmById(id);
    }
}
