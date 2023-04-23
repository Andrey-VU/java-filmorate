    CREATE TABLE IF NOT EXISTS USERS (
	user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	user_name CHARACTER VARYING(255) NOT NULL,
	BIRTHDAY CHARACTER VARYING(255) NOT NULL,
	LOGIN CHARACTER VARYING(255) NOT NULL,
	EMAIL CHARACTER VARYING(255) NOT NULL
);

    CREATE TABLE IF NOT EXISTS FRIENDS (
	FRIEND_TO INTEGER NOT NULL,
	F_USER_ID INTEGER NOT NULL,
	CONSTRAINT FRIENDS_FK FOREIGN KEY (F_USER_ID) REFERENCES USERS(USER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FRIENDS_FK_1 FOREIGN KEY (FRIEND_TO) REFERENCES USERS(USER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

    CREATE TABLE IF NOT EXISTS GENRE (
    genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	genre_name CHARACTER VARYING(255),
	UNIQUE (genre_name)
);

    CREATE TABLE IF NOT EXISTS RATE_MPA (
	rate_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	rate_name CHARACTER VARYING(255) NOT NULL,
	UNIQUE (rate_name)
);

    CREATE TABLE IF NOT EXISTS FILMS (
	FILM_ID INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	NAME CHARACTER VARYING(255) NOT NULL,
	DESCRIPTION CHARACTER VARYING(500) NOT NULL,
	RELEASE_DATE CHARACTER VARYING(255) NOT NULL,
	DURATION INTEGER NOT NULL,
	RATE_ID INTEGER,
	CONSTRAINT FILMS_FK FOREIGN KEY (RATE_ID) REFERENCES RATE_MPA(RATE_ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

    CREATE TABLE IF NOT EXISTS GENRES_FILMS (
	FILM_ID INTEGER NOT NULL,
	GENRE_ID INTEGER NOT NULL,
	CONSTRAINT GENRES_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES FILMS(FILM_ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT GENRES_FILMS_FK2 FOREIGN KEY (GENRE_ID) REFERENCES GENRE(GENRE_ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);

    CREATE TABLE IF NOT EXISTS FAN_LIST (
	FILM_ID_F INTEGER NOT NULL,
	USER_ID INTEGER NOT NULL,
	CONSTRAINT FAN_LIST_FK FOREIGN KEY (FILM_ID_F) REFERENCES FILMS(FILM_ID) ON DELETE RESTRICT ON UPDATE RESTRICT,
	CONSTRAINT FAN_LIST_FK_1 FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID) ON DELETE RESTRICT ON UPDATE RESTRICT
);