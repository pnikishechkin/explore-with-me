# Explore With Me (Исследуй со мной)

**Приложение позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них.**

## Архитектура

Имеет микросервисную архитектуру, в которую входит 2 сервиса: основной сервис и сервис статистики.

### Основной сервис 

Реализует основные функции продукта:

- пользователям: добавление, редактирование, удаление событий, отправка запросов на участие
- пользователям: поиск событий с возможностью фильтрации и сортировки
- администраторам: создавать подборки событий и управлять категориями
- администраторам: управлять данными пользователей

Спецификация API: 

Коллекция запросов postman: [ewm-main-service.json](https://github.com/user-attachments/files/18442527/ewm-main-service.json)

Модель данных:
![erd_main_service](https://github.com/user-attachments/assets/8f52472e-66dc-4f25-9b5f-406777e145c9)

### Сервис статистики 

Собирает статистику о количестве просмотров события и позволяет делать различные выборки для анализа.

Спецификация API: 

Коллекция запросов postman: 

Модель данных:

