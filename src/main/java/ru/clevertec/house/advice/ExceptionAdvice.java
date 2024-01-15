package ru.clevertec.house.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.house.advice.model.Response;
import ru.clevertec.house.exception.BadClientRequestException;
import ru.clevertec.house.exception.EntityNotFoundException;

/**
 * Класс для обработки exception и вывод из в нужном формате json
 */

@ControllerAdvice
public class ExceptionAdvice {

    /**
     * Метод работает с исключением EntityNotFoundException
     *
     * @param e объект исключения
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Response> handlerNotFoundEntityException(EntityNotFoundException e) {
        Response response = new Response(e.getMessage(), "40401");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод работает с исключением BadClientRequestException
     *
     * @param e объект исключения
     */
    @ExceptionHandler(BadClientRequestException.class)
    public ResponseEntity<Response> handlerBadRequestException(BadClientRequestException e) {
        Response response = new Response(e.getMessage(), "40001");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
