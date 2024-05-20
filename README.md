Ссылка на установку Java      https://metanit.com/java/tutorial/1.6.php 
Необходимо будет скачивать именно OpenJDK, а не OracleJDK
Ссылка на инструкцию по установке Intellij Idea      https://metanit.com/java/tutorial/1.5.php
Инструкция по установке с MySQL      https://metanit.com/sql/mysql/1.1.php
В MySQL Command Line Client нужно будет ввести эти комманды, чтоб подключить БД к проекту:

mysql> create database service;
mysql> create user 'brawl'@'%' identified by 'stars';
mysql> grant all on service.* to 'brawl'@'%';
