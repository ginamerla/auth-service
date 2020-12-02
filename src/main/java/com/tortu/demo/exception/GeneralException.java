package com.tortu.demo.exception;

/**
 * Excepcion personalizada.
 *
 */
public class GeneralException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public GeneralException(String message){
        super(message);
    }

    public GeneralException(Throwable e) {
        super(e);
    }

    public GeneralException(String message, Throwable e) {
        super(message, e);
    }
}