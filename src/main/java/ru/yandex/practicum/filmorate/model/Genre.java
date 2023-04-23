package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public class Genre {
    private String name;
    private int id;

    public Genre(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Genre(int id) {
        this.id = id;
        switch (id) {
            case 1:
                this.name = "Комедия";
                break;
            case 2:
                this.name = "Драма";
                break;
            case 3:
                this.name = "Мультфильм";
                break;
            case 4:
                this.name = "Триллер";
                break;
            case 5:
                this.name = "Документальный";
                break;
            case 6:
                this.name = "Боевик";
                break;
        }

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
