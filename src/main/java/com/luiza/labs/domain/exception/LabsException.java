package com.luiza.labs.domain.exception;

/**
 * Classe base abstrata para as exceções internas da aplicação.
 */
public abstract class LabsException extends Exception {

    protected LabsException(String message) {
        super(message);
    }
}
