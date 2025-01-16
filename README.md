# Explore With Me

**Приложение позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них.**
Имеет микросервисную архитектуру, в которую входит 2 сервиса: основной сервис и сервис статистики.

## Основной сервис 

Реализует основные функции продукта:

- пользователям: добавление, редактирование, удаление событий, отправка запросов на участие
- пользователям: поиск событий с возможностью фильтрации и сортировки
- администраторам: создавать подборки событий и управлять категориями
- администраторам: управлять данными пользователей

Спецификация API: [ewm-main-service-spec.json](https://github.com/user-attachments/files/18442548/ewm-main-service-spec.json)

Коллекция запросов postman: [ewm-main-service.json](https://github.com/user-attachments/files/18442527/ewm-main-service.json)

Модель данных:

![erd_main_service](https://github.com/user-attachments/assets/8f52472e-66dc-4f25-9b5f-406777e145c9)

## Сервис статистики 

Собирает статистику о количестве просмотров события и позволяет делать различные выборки для анализа.

Спецификация API: [ewm-stats-service-spec.json](https://github.com/user-attachments/files/18442555/ewm-stats-service-spec.json)

Коллекция запросов postman: [ewm-stat-service.json](https://github.com/user-attachments/files/18442562/ewm-stat-service.json)

Модель данных:

![erd_stats](https://github.com/user-attachments/assets/0da0194a-f771-4aad-bb8c-9c7bbcb6c611)


