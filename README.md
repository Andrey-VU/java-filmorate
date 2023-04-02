# java-filmorate
Template repository for Filmorate project.
## project of BD 
![ER-диаграмма](https://raw.githubusercontent.com/Andrey-VU/java-filmorate/bd_project/Проект%20БД%20Filmorate.png)

## Примеры запросов 

### Вывести все фильмы с определенным жанром 
SELECT f.film_id
FROM films AS f
JOIN jenre AS j ON j.jenre_id = f.jenre_id
WHERE j.name = 'this_name'; 

### Вывести названия всех фильмов, которые нравятся пользователям с id 1  
SELECT f.name
FROM users AS u
JOIN fanList AS fl ON f.film_id = fl.film_id
WHERE fl.user_id = 1; 





