CREATE TABLE categories(
id INT PRIMARY KEY NOT NULL,
name TEXT NOT NULL
);

INSERT INTO categories(id, name) VALUES(1,'Много новостей'),(2,'Немного новостей'),(3,'Нет новостей');

CREATE TABLE news(
id INT PRIMARY KEY NOT NULL,
title TEXT NOT NULL,
date VARCHAR(30) NOT NULL,
short_description TEXT,
full_description TEXT,
category_id INT references categories(id)
);

INSERT INTO news (id,title,date,short_description,full_description,category_id) Values
(1,'Парочка туристов случайно воспользовалась «личным самолетом»','26.08.2020','Семейная пара из Великобритании ощутила прелести полета на личном самолете, оказавшись единственными пассажирами на обычном пассажирском рейсе.','<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>',1),
(2,'Манулу очень грустно :(','20.08.2020','Вот веселый был, а потом вдруг загрустил ;((((','<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>',1),
(3,'Ворьби вымерли','20.12.2042','Вымер последний вид птиц на земле','<p>Полный текст новости <b>с жирным текстом</b>, <i>курсивом</i> и <a href=\"http://testtask.sebbia.com/swagger-ui.html\">одной ссылкой</a></p><p>Кроме того, в тексте два параграфа</p>',2);
