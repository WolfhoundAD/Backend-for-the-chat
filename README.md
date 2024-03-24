"# Rest_and_JavaSpring" 
# Задание
Создать REST приложение **(Shopping Cart)** с использованием Spring.
- Там, где для таблиц указано "принимает одно из", означает, что в entity будет enum
- Данные принимаем в виде json
- Работа с БД должна быть организована через спринговый JpaRepository (пакет repository). Jpa-реализация - hibernate
- в проекте должен быть Swagger

Для работы реализовать rest controller-ы со следующим api:

Order
1) Создать заказ (пустой, без товаров)
2) Удалить заказ (удаляется вместе со всеми товарами заказа)
3) Поменять статус заказа
4) Отобразить заказ по id

OrderDetail
1) Добавить позицию
2) Удалить позицию
3) Отобразить все позиции по order_id

Для Product
1) возможность вывести все товары, которые есть в наличие
---
### Создание
Поект был создан в **[IntelliJ IDEA Ultimate]**. В качестве сборщика испульзуется **[Maven]**.

### База данных
Используемая бд - **[PostgreSQL]**
![PostgreSQL](https://l.ruby-china.com/photo/2019/de7a7028-6c2b-40b6-b1dc-33f2e843060b.png!large)

##### Таблицы в бд:
Входные данные при запуске приложения ***src.main.resources.database***.
- Customer (id, name, address, email, phone) - Покупатель
- Order (id, order_date, order_status, payment_method, customer_id) – Заказ
    - customer_id – ссылка на покупателя;
    - order_status – принимает одно из created, processing, cancelled, shipped;
    - payment_method – принимает одно из CREDIT_CARD, PAY_PAL, ON_RECEIPT
- Product (id, name, price, description, in_stock) – Товар
    - (in_stock – логическое поле, есть ли товар в продаже)
- OrderDetail (id, order_id, product_id, price, quantity).
    - Т.к. история цен продуктов не ведется, то в поле price сохраняется актуальная на момент оформления заказа цена -  берется из Product.price.
"# Backend-for-Angular-example" 
