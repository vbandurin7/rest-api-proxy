# rest-api-proxy

### Практическое задание: реализовать rest api для перенаправления запросов на _https://jsonplaceholder.typicode.com_

### Запуск:

1) Сделать `docker-compose up -d` .yaml файла в папке [src](src). Он поднимет PostgreSQL и pgAdmin для удобного взаимодействия
с БД.

2) Запустить Main() в [RestApiProxyApplication.java](src%2Fmain%2Fjava%2Fcom%2Fvk%2Frestapiproxy%2FRestApiProxyApplication.java)

Данные для входа в pgAdmin:
```
- email: root@mail.ru
- password: root
```

После входа и подключения к серверу появляется возможность добавлять руками новые сущности при необходимости.

### Вспомогательные возможности

1) После запуска приложения по пути `/home` доступна домашняя страница с предложениями по регистрации, входу в аккаунт,
выходу из аккаунта, а также получением информации по нескольким конечным точкам.

2) Во время инициализации бинов в базу добавляется несколько тестовых пользователей:
```
- username: admin, password: 123, role: ADMIN
- username: poster, password: 123, role: POSTS_EDITOR
- username: uviewer, password: 123, role: USERS_VIEWER
```
Соответственно, под каждым из них можно авторизоваться (`/login`) и проверить работоспособность :)
