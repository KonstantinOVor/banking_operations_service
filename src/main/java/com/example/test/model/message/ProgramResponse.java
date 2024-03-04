package com.example.test.model.message;

public enum ProgramResponse {

    USER_NOT_FOUND ("Пользователь с идентификатором {} не найден: "),
    INVALID_CONTACT_TYPE ("Указан неверный тип контакта"),
    PHONE_NOT_FOUND ("Номер телефона не найден"),
    USER_CREATED ("Создан пользователь: {}"),
    PHONE_UPDATED ("Телефон изменен: {}"),
    PHONE_DELETED ("Телефон удален: {}"),
    EMAIL_UPDATED ("Email изменен: {}"),
    EMAIL_DELETED ("Email удален: {}"),
    EMAIL_ALREADY_EXISTS ("Email уже существует"),
    EMAIL_IS_ALREADY_REGISTERED ("Введенный email уже зарегистрирован"),
    PHONE_ALREADY_EXISTS ("Номер телефона уже существует"),
    LAST_PHONE_CANNOT_BE_DELETED ("Невозможно удалить последний номер телефона"),
    LAST_EMAIL_CANNOT_BE_DELETED ("Невозможно удалить последний Email"),
    BANK_ACCOUNT_CREATED ("Банковский счет создан: {}"),
    BANK_ACCOUNT_DELETED ("Банковский счет удален: {}"),
    BANK_ACCOUNT_UPDATED ("Банковский счет изменен: {}"),
    BANK_ACCOUNT_ALREADY_EXISTS ("Банковский счет уже существует"),
    BANK_ACCOUNT_NOT_FOUND ( "Банковский счет не найден"),
    INSUFFICIENT_BALANCE ("Недостаточно средств"),
    TRANSACTIONS_FOUND ("Транзакции найдены"),
    INVALID_BANK_ACCOUNT_TYPE ("Указан неверный тип банковского счета"),
    LAST_BANK_ACCOUNT_CANNOT_BE_DELETED ("Невозможно удалить последний банковский счет"),
    BALANCE_UPDATED ("Баланс изменен: {}"),
    TRANSACTION_CREATED ("Транзакция создана: {}"),
    TRANSACTION_NOT_FOUND ("Транзакция не найдена"),
    OPTIMISTIC_LOCKING_FAILURE ( "Оптимистическая блокировка не удалась"),
    TRANSACTION_DELETED ("Транзакция удалена: {}"),
    INVALID_DATE_OF_BIRTH ("Неверный формат даты рождения. Должно быть в формате: 'dd.MM.yyyy"),
    INVALID_BALANCE ("Сумма баланса указана не верно! Баланс должен быть больше 0 и меньше {} "),
    INVALID_PHONE ("Неверный формат телефонного номера. Должно быть в формате: 81234567890"),
    EMPTY_PHONE ("Отсутствует обязательное поле 'Номер телефона'"),
    INVALID_FULL_NAME ("Неверное имя"),
    EMAIL_NOT_FOUND ("Email не найден"),
    EMPTY_EMAIL("Отсутствует обязательное поле 'Email'"),
    INVALID_EMAIL("Неверный формат 'Email'. Должен быть в формате: 'wwww@example.com'"),
    EMPTY_SEARCH_PARAMS("Не заданы параметры для поиска"),
    EMPTY_FULL_NAME("Отсутствует обязательное поле 'Полное имя'"),
    PHONE_IS_ALREADY_REGISTERED("Введенный номер телефона уже зарегистрирован"),
    EMPTY_USERNAME("Отсутствует обязательное поле 'Логин'"),
    USERNAME_IS_ALREADY_REGISTERED("Введенный логин уже зарегистрирован"),
    EMPTY_PASSWORD("Отсутствует обязательное поле 'Пароль'"),
    INVALID_PASSWORD("Неверный формат пароля. Пароль должен содержать: цифры и буквы"),
    FULL_NAME_NOT_FOUND("Не найден пользователь с таким именем"),
    DATE_OF_BIRTH_NOT_FOUND("Не найдены пользователи с указанной датой рождения"),;

    private final String description;

    ProgramResponse(String description) {

        this.description = description;
    }
    public String getDescription() {

        return description;
    }
}
