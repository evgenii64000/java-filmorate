package ru.yandex.practicum.filmorate.controller;

import java.util.HashSet;

public abstract class Controller <T> {
    private final HashSet<T> list = new HashSet<T>();
    private long idCounter = 0;

    protected long generateId() {
        idCounter++;
        return idCounter;
    }

    public T post(T t) {
        list.add(t);
        return t;
    }

    public T update(T t) {
        list.remove(t);
        list.add(t);
        return t;
    }

    public HashSet<T> returnList() {
        return list;
    }
}
