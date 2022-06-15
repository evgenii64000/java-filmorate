package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashSet<Film> films = new HashSet<Film>();
    private long idCounter = 0;

    @Override
    public long generateId() {
        idCounter++;
        return idCounter;
    }

    @Override
    public Film create(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        film.setId(generateId());
        log.info("Добавлен фильм");
        films.add(film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("Релиз фильма раньше 28.12.1895");
            throw new ValidationException("Дата релиза должна быть не раньше 28 декабря 1895");
        }
        if (!films.contains(film)) {
            log.warn("Фильма с таким id нет");
            throw new NotFoundException("Фильма с таким id нет");
        }
        log.info("Фильм обновлён");
        films.remove(film);
        films.add(film);
        return film;
    }

    @Override
    public void delete(Film film) {
        films.remove(film);
    }

    @Override
    public HashSet<Film> getFilms() {
        return films;
    }

    @Override
    public Film getFilmById(Long id) {
        Optional<Film> optionalFilm = films.stream()
                .filter(film -> film.getId() == id)
                .findFirst();
        if (!optionalFilm.isPresent()) {
            log.warn("Фильма с таким id нет");
            throw new NotFoundException("Фильма с таким id нет");
        } else {
            return optionalFilm.get();
        }
    }
}
