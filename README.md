# Управление студентами (Backend)

Это приложение представляет собой backend-часть системы управления студентами. Оно предоставляет API для управления студентами и пользователями системы.

## Технологии

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Swagger
- BCryptPasswordEncoder

API Endpoints:
  GET /api/base/students: Получить список всех студентов.
  POST /api/base/students: Создать нового студента.
  PUT /api/base/students/{id}: Обновить информацию о студенте с указанным идентификатором.
  DELETE /api/base/students/{id}: Удалить студента с указанным идентификатором.
  GET /api/base/students/{id}: Получить информацию о студенте с указанным идентификатором.
Авторизация
Авторизация осуществляется через HTTP Basic Authentication.
Роли:
  STUDENT
  ADMIN
Инициализация данных
Приложение автоматически инициализирует базу данных при запуске, добавляя обычного студента и администратора.
