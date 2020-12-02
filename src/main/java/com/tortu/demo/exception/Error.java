package com.tortu.demo.exception;

/**
 * Modelo Error. Esta clase representa la entidad "error" en calquier parte de la aplicacion.
 * @author visilva
 */
public class Error {
    private String errorMessage;
    private String cause;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
