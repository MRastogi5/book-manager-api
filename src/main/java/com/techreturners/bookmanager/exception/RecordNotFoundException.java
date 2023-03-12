package com.techreturners.bookmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RecordNotFoundException extends RuntimeException {
    private String message;

        public RecordNotFoundException(String message) {
            super(message);
            this.message = message;
        }

        public RecordNotFoundException() {
        }
}
