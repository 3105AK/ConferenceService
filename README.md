Java скорее всего автоматически подключится когда ты откроешь проект в Intellij Idea (Ну она естественно попросит на то разрешение)
Но если что, вот ссылка на инструкцию по установке      https://metanit.com/java/tutorial/1.6.php 
Необходимо будет скачивать именно OpenJDK, а не OracleJDK, так как там нет замут с лицензией, а со второй тебя могут засудить, если используешь в коммерческих целях
Ссылка на инструкцию по установке Intellij Idea      https://metanit.com/java/tutorial/1.5.php
Тебе не надо будет потом создавать новый проект, а надо будет просто через неё открыть это проект
Инструкция MySQL      https://metanit.com/sql/mysql/1.1.php
Тебе не будут нужны графические клиенты (приложения) для работы с БД, тебе вполне хватит консольного клиента, краткая информация как им пользоваться      https://metanit.com/sql/mysql/1.2.php
Дальше в MySQL Command Line Client нужно будет ввести эти комманды, чтоб подключить БД к проекту ("mysql>" вводить не нужно):

mysql> create database service;
mysql> create user 'brawl'@'%' identified by 'stars';
mysql> grant all on service.* to 'brawl'@'%';

Все html формы нужно будет кидать в resources\templates
CSS файлы, картинки и скорее всего JS файлы в resources\static
Не забывать, что все взамодействия между файлами обеспечивает Spring Framework, поэтому надо все ссылки подключать через Thymeleaf
Чтоб протестировать проект, нужно зайти в класс src\main\java\com\brawlstars\ConferenceService\ConferenceServiceApplication.java и нажать сверху зелённый треугольник
Далее переходишь на адресс     http://localhost:8080/namePage    где namePage - это имя добавленной html-страницы (На данный момент доступны варинаты enter, room, createRoom)
http://localhost:8080/    перекидыват на страницу с именем home (ну естественно когда ты её создашь)
