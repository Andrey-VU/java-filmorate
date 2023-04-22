package ru.yandex.practicum.filmorate.model;
import java.util.Objects;

public class Mpa {
    private String name;
    private int id;

    public Mpa(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public Mpa(int id) {
        this.id = id;
        switch (id) {
            case 1:
                this.name = "G";
                break;
            case 2:
                this.name = "PG";
                break;
            case 3:
                this.name = "PG-13";
                break;
            case 4:
                this.name = "R";
                break;
            case 5:
                this.name = "NC-17";
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
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Mpa{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // проверяем адреса объектов
        if (obj == null) return false; // проверяем ссылку на null
        if (this.getClass() != obj.getClass()) return false; // сравниваем классы
        Mpa otherMpa = (Mpa) obj; // открываем доступ к полям другого объекта
        return (id == otherMpa.getId()) && // проверяем все поля
               name.equals(otherMpa.getName());
    }
}

/*
G — у фильма нет возрастных ограничений,
PG — детям рекомендуется смотреть фильм с родителями,
PG-13 — детям до 13 лет просмотр не желателен,
R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого,
NC-17 — лицам до 18 лет просмотр запрещён.
 */
