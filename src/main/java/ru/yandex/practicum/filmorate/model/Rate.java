package ru.yandex.practicum.filmorate.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Rate {
    private Map<Integer,String> allRates = new HashMap<>();
    private int id;
    private String name;

    public Rate(int id, String name) {
        allRates.put(1, "G");
        allRates.put(2, "PG");
        allRates.put(3, "PG-13");
        allRates.put(4, "R");
        allRates.put(5, "NC-17");
        this.id = id;
        this.name = name;
    }

    public int getRateId() {
        return id;
    }

    public void setRateId(int id) {
        this.id = id;
        this.name = allRates.get(id);
    }

    public String getRateName() {
        return name;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // проверяем адреса объектов
        if (obj == null) return false; // проверяем ссылку на null
        if (this.getClass() != obj.getClass()) return false; // сравниваем классы
        Rate otherRate = (Rate) obj; // открываем доступ к полям другого объекта
        return (id == otherRate.id) && // проверяем все поля
               Objects.equals(name, otherRate.name);
    }
}
