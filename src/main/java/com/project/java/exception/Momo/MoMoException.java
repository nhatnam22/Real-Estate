package com.project.java.exception.Momo;


public class MoMoException extends Exception {
    public MoMoException(String message) {
        super(message);
    }

    public MoMoException(Throwable cause) {
        super(cause);
    }
}