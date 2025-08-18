# CloudKube

Spring Boot приложение с REST API для управления пользователями, постами, дружбой и изображениями.

## Технологии

- **Backend**: Spring Boot 3.x, Spring Security, JWT
- **База данных**: PostgreSQL, MongoDB
- **Файловое хранилище**: MinIO
- **WebSocket**: для чата в реальном времени

## Запуск с Docker

### 1. Запуск сервисов

```bash
docker-compose up -d
```

Это запустит:
- **PostgreSQL** на порту 5432
- **MinIO** на порту 9000 (API) и 9001 (консоль)
- **MongoDB** на порту 27017

### 2. Доступ к сервисам

- **PostgreSQL**: `localhost:5432`
  - База данных: `cloudkube`
  - Пользователь: `postgres`
  - Пароль: `postgres`

- **MinIO Console**: `http://localhost:9001`
  - Пользователь: `minioadmin`
  - Пароль: `minioadmin`

- **MongoDB**: `localhost:27017`
  - База данных: `cloudkube`
  - Пользователь: `admin`
  - Пароль: `admin`

### 3. Запуск приложения

```bash
./mvnw spring-boot:run
```

Приложение будет доступно на `http://localhost:8080`

## REST API Endpoints

### Аутентификация
- `POST /login` - вход пользователя
- `POST /register` - регистрация пользователя

**Важно**: Все остальные эндпоинты требуют JWT авторизации в заголовке `Authorization: Bearer <token>`

### Пользователи
- `GET /users` - получить всех пользователей
- `GET /users?username={username}` - поиск пользователей по имени
- `GET /users/{id}` - получить профиль пользователя
- `GET /users/{id}/friends` - получить друзей пользователя
- `POST /users/add-friend` - добавить друга
- `GET /users/create-post` - форма создания поста
- `POST /users/posts` - создать пост

### Изображения
- `POST /image` - загрузить изображение пользователя
- `GET /images/{fileName}` - получить изображение

### Чат
- `GET /chat` - получить сообщения чата
- WebSocket endpoints для реального времени

## Безопасность

Приложение использует JWT-токены для аутентификации. Все эндпоинты защищены, кроме:
- `/login` и `/register` - для аутентификации
- `/images/**` - для доступа к изображениям
- `/static/**`, `/css/**`, `/js/**` - для статических ресурсов

Подробная информация о безопасности: [README_SECURITY.md](README_SECURITY.md)

## Структура проекта

```
src/main/java/io/unitbean/
├── config/          # Конфигурации Spring
├── controller/      # REST контроллеры
├── dto/            # Data Transfer Objects
├── exception/      # Обработчики исключений
├── model/          # JPA сущности
├── repository/     # Репозитории для работы с БД
├── service/        # Бизнес-логика
└── utils/          # Утилиты (JWT, фильтры)
```

## Остановка сервисов

```bash
docker-compose down
```

Для удаления данных:
```bash
docker-compose down -v
``` 