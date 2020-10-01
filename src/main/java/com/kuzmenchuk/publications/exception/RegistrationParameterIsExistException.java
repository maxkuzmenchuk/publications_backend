package com.kuzmenchuk.publications.exception;

public class RegistrationParameterIsExistException extends RuntimeException{
    public RegistrationParameterIsExistException() {
    }

    public RegistrationParameterIsExistException(String message) {
        super(message);
    }

    public RegistrationParameterIsExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationParameterIsExistException(Throwable cause) {
        super(cause);
    }
}
