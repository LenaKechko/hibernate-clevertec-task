package ru.clevertec.house.exception;

/**
 * Класс exception в случае не верного запроса от пользователя
 */
public class BadClientRequestException extends RuntimeException {

    public BadClientRequestException(String message) {
        super(message);
    }

    public static BadClientRequestException of(Class<?> clazz, Object obj) {
        return new BadClientRequestException(clazz.getSimpleName() + "bad request (" + obj + ")");
    }
}
