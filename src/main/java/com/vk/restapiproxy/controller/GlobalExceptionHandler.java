package com.vk.restapiproxy.controller;

import com.vk.restapiproxy.dto.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResponse> notFoundHandler(Exception e) {
        return createErrorResponse("Page Not Found", HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> wrongArgumentHandler(Exception e) {
        return createErrorResponse("Invalid arguments", HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> exceptionHandler(Exception e) {
        return createErrorResponse("Unexpected exception", HttpStatus.BAD_REQUEST, e);
    }

    @SuppressWarnings("SameParameterValue")
    private ResponseEntity<ApiErrorResponse> createErrorResponse(String message, HttpStatus httpStatus, Exception e) {
        return new ResponseEntity<>(new ApiErrorResponse(
                message, Integer.toString(httpStatus.value()),
                e.getClass().getSimpleName(), e.getMessage()), httpStatus);
    }
}
