package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;

public interface FilmStorage {

    long generateId();

    Film create(Film film);

    Film update(Film film);

    void delete(Film film);

    HashSet<Film> getFilms();

    Film getFilmById(Long id);
}
