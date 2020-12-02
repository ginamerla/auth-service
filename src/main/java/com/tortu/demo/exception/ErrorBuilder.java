package com.tortu.demo.exception;

/**
 * Clase que construye de manera controlada el objeto Error.
 * "Builder" design pattern.
 *
 * @author visilva
 */
public class ErrorBuilder {
    private String errorMessage;
    private String causeMessage;

    /**
     * Constructor default.
     */
    public ErrorBuilder() {
        // nothing to do here.
    }

    public ErrorBuilder setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public ErrorBuilder setCause(Throwable cause) {
        if (cause == null) {
            return this;
        }
        this.causeMessage = cause.getMessage();
        return this;
    }

    /**
     * Metodo que generara el objeto error de manera controlada.
     *
     * @return Objeto error.
     */
    public Error build() {
        Error error = new Error();
        error.setCause(causeMessage);
        error.setErrorMessage(errorMessage);
        return error;
    }
}
