package ru.yandex.practicum.filmorate.model;

public enum Rate {
    G,
    PG,
    PG_13,
    R,
    NC_17
}


/*
для перевода PG_13 и NC_17
Можно использовать enum, в маппере потом из строки методом valueOf можно получать enum + toString
в обратном направлении. Можно задать поле внутри енама со значением,
 которое мы хотим видеть в бд и мапить енам в ту и обратную сторону по этому полю
 */