Данный проект представляет из себя веб-приложение (интернет-магазин).

<details><summary>Функционал</summary>
На главной странице расположен блок с кнопками, можно войти в аккаунт или зарегистрироваться:

![image](https://github.com/maksim25y/ShopApp/assets/131711956/061a328f-dd0f-437f-856e-51e7b26d8426)

При нажатии на кнопку "Регистрация" происходит переход на страницу регистрации:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/9a8cc8d7-1b86-4f78-9fec-715afb946834)

Добавлена валидация, в случае ввода некорректных данных будет выведено соответствующее сообщение
![image](https://github.com/maksim25y/ShopApp/assets/131711956/ba342fde-1e1e-41a6-8259-6b39eeb79839)

При нажатии на кнопку "Войти" пользователь перейдёт на страницу входа в аккаунт.
![image](https://github.com/maksim25y/ShopApp/assets/131711956/01b40196-fe43-47aa-8787-454bbb0cc7d2)

Если пользователь ввёл некорректные данные, то будет выведено соответствующее оповещени:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/33f07a56-236e-406b-a183-44f1463b79d0)

При корректном вводе данных пользователь попадает на главную страницу сайта, кнопки "Войти" и "Регистрация" пропадают, появляются кнопка "Мой профиль" и "Список товаров":
![image](https://github.com/maksim25y/ShopApp/assets/131711956/c0280c5f-1ca6-4a56-83e2-c058a2617673)

Нажав на кнопку "Список товаров" пользователь попадает на страницу с доступными товарами и может перейти на страницу любого товара:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/ae6ff76c-65a7-4a68-a513-2fa3bc11bd11)
При переходе на страницу товара пользователь увидит описание товара и сможет забронировать товар, нажав на кнопку "Добавить в корзину" (товар добавится в корзину, которую пользователь может просмотреть в его профиле):
![image](https://github.com/maksim25y/ShopApp/assets/131711956/64eec9bf-d78a-4a10-95b5-ccc972338b01)

При переходе в свой профиль пользователь сможет увидеть информацию профиля, сможет отредактировать данные профиля:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/a4b2807d-6e7c-4e90-961e-517c58392919)
Добавлена валидация и при некорректном вводе данных для редактирования будет выведено сообщение:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/9cc0e582-6408-4a5c-8a9f-2f084d325dfe)
При нажатии на кнопку "Моя корзина" пользователь может просмотреть товары в его корзине:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/2520d08d-5b78-4278-84a3-5f809ee134bc)
Если товар добавляется в корзину пользователя, то он пропадает из общего списка товаров и больше не доступен для других пользователей (кроме администраторов), при нажатии на кнопку "Удалить из корзины" товар будет удалён из корзины и возвращён в список товаров.
При регистрации пользователь указывает свою почту и изначально она не подтверждена, но если пользователь нажмёт на кнопку для подтверждения ему на почту будет направлено письмо с кодом подтверждения:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/116c226d-5419-474b-8102-35adeb9a6568)
После ввода кода подтверждения почта становится подтверждённой и надпись в профиле о необходимости опдтверждения пропадает.

Администратор может добавлять, удалять, редактировать товары, редактироватьm регистрировать и удалять пользователей:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/9103f6da-8c86-4979-9e66-54ba705d827f)
![image](https://github.com/maksim25y/ShopApp/assets/131711956/795cb3fd-4951-40bd-9170-af5177cdb0e1)
![image](https://github.com/maksim25y/ShopApp/assets/131711956/905bfca1-e2d8-403d-8b47-e28f7a6a76aa)
![image](https://github.com/maksim25y/ShopApp/assets/131711956/37854085-5a12-4227-88ad-8f02ebc0b048)
Форма создания записи:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/f84c4849-a5cf-4f9f-a67c-dd46cda8316f)
Созданная запись:
![image](https://github.com/maksim25y/ShopApp/assets/131711956/009d060a-76b4-40a3-8e43-e17229d6b620)

</details>
<details><summary>Реализация</summary>
Во время выполнения проекта я использовал следующий набор технологий: PostgreSQL, Maven, Spring MVC, Spring Data JPA, Security, ORM Hibernate, Bootstrap, Thymeleaf, Spring Validation.
  
База данных:

![image](https://github.com/maksim25y/ShopApp/assets/131711956/6c8cee56-82a6-428d-a4eb-917834601c90)


</details>
<details><summary>Запуск</summary>
Для того, чтобы запустить необходимо проделать следующие шаги на Windows, установите [Git Bash](https://git-scm.com/)

1. Склонируйте репозиторий

```shell
git clone git@github.com:maksim25y/ShopApp.git
```

2. Скачайте и установите Docker

Скачать и найти инструкцию по установке вы можете на официальном сайте [Docker](https://www.docker.com)

3. Запустите сайт в Docker

Для этого откройте терминал и перейдите в папку репозитория

```shell
cd ShopApp
```

#### Переменные окружения в .env

Описание:
1. POSTGRES_DB - имя базы данных
2. POSTGRES_HOST - хост базы данных (в данном случае имя сервиса в docker-compose)
3. POSTGRES_PASSWORD - пароль от базы данных
4. POSTGRES_PORT - порт, требующийся для работы базы данных
5. POSTGRES_USER - имя пользователя базы данных
6. SECRET_KEY - секретный ключ от вашего сайта

Далее введите команду

```shell
docker-compose up --build
```
Готово! Сервер запущен.
Чтобы зайти на сайт перейдите по ссылке: localhost:8080

Чтобы остановить работу контейнеров, в терминале, откуда вы запускали docker-compose нажмите Ctrl+C (Control + C для Mac)
</details>