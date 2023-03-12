package com.techreturners.bookmanager.service;

public class RecordAlreadyExistsException extends Throwable {
    private String message;

    public RecordAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public RecordAlreadyExistsException() {
    }
}
