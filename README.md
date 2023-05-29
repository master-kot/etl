# etl
Описание функционала микросервиса etl, вытаскивающего и поддерживающего консистентные данные из системы legacy-app:
1) Получать данные через REST API при получении события и сохранять их в базу данных
2) Запустить однократную переливку данных через REST API в назначенную дату, либо по запросу

Схема таблицы:
- id                   bigserial,
- type                 text,         // возможные типы: TYPE1, TYPE2, TYPE3, TYPE4, TYPE5, TYPE6, TYPE7
- business_value       text,
- created_at           timestamp,
- updated_at           timestamp,

Схема события в топике Kafka:
- «id»: 1234567890           // long айдишник в таблице
- «action»: «CREATE»,        // строка-енам возможны виды: CREATE, UPDATE, DELETE
- «eventTime»: 1234567890    // long unix-time

Техстек:
- Java 17, Spring Boot 3, PostgreSQL, Kafka
