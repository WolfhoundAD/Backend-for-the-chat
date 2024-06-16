
# Используем официальный образ OpenJDK в качестве базового образа
FROM openjdk:11-jre-slim

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем jar файл в контейнер
COPY target/*.jar /app/chat-app.jar

# Указываем команду для запуска приложения
ENTRYPOINT ["java", "-jar", "chat-app.jar"]
