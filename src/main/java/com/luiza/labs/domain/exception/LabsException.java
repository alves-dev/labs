package com.luiza.labs.domain.exception;

public abstract class LabsException extends Exception {

    protected LabsException(String message) {
        super(message);
    }
}
