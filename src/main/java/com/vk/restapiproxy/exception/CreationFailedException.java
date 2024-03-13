package com.vk.restapiproxy.exception;

public class CreationFailedException extends RuntimeException {

    public CreationFailedException(String message) {
        super(message);
    }
}
