package ru.clevertec.house.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Класс exception в случае если сервер возвращает статус 500
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Some field is not valid")
public class ValidateException extends RuntimeException {
}