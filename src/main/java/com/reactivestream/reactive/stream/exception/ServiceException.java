package com.reactivestream.reactive.stream.exception;

public class ServiceException extends RuntimeException{
    String message;

    public ServiceException(String message) {
        this.message = message;
    }
}
