# Мессенджер (Backend)

Это приложение представляет собой backend-часть мессенджера, обеспечивающего обмен сообщениями между пользователями. 
Оно предоставляет API для управления пользователями, профилями, чатами и сообщениями.
## Технологии

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Swagger
- BCryptPasswordEncoder

API Endpoints:
 в разработке

Роли:
  STUDENT
  ADMIN
Инициализация данных
Приложение автоматически инициализирует базу данных при запуске, добавляя обычного студента и администратора.

Для запуска проекта необходмо произвести следущие шаги:
    1. В application.yml поставить профиль "prod"
    2. Зайти в папку с dockerfile и написать две команды: "mvn clean package" и "docker-compose up --build"
    3. Также для полноты картины необходимо запустить frontend часть(см. другой репозиторий)
    4. Хорошего опыта пользования! =)