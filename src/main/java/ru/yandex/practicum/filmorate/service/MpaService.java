package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDbStorage;
import ru.yandex.practicum.filmorate.model.RateMpa;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
public class MpaService {
    private final MpaDbStorage mpaDbStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaDbStorage = mpaDbStorage;
    }

    public Collection<RateMpa> getRates() {
        return mpaDbStorage.getRates();
    }

    public Optional<RateMpa> getRateById(int id) {
        return mpaDbStorage.getRateById(id);
    }
}
