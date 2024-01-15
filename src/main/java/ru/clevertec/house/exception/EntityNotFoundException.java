package ru.clevertec.house.exception;

/**
 * Класс для exception в случае, если искомая сущность не существует
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Class<?> clazz, Object obj) {
        return new EntityNotFoundException(clazz.getSimpleName() + " not fount (uuid = " + obj + ")");
    }
}
