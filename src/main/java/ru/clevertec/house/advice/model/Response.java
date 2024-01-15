package ru.clevertec.house.advice.model;

import lombok.Getter;

@Getter
public class Response {

    private String errorMessage;
    private String errorCode;

    public Response() {
    }

    public Response(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}
