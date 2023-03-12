package com.techreturners.bookmanager.exception;

public class RecordAlreadyExistsException extends Throwable {
    private String message;

    public RecordAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public RecordAlreadyExistsException() {
    }
}