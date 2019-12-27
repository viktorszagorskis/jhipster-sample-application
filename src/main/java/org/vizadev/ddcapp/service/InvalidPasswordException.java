package org.vizadev.ddcapp.service;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Incorrect password");
    }

}
