package com.tortu.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Esta clase genera la extructura general de cualquier error cuando una excepcion falla, regresando un error 500
 * siempre si algo falla.
 * @author visilva
 */
@Component("exceptionMapper")
@Provider
public class ExceptionMapperImplementation implements ExceptionMapper<Exception> {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionMapperImplementation.class);
    private static final Integer DEFAULT_STATUS_ERROR_CODE = 500;
    @Override
    public Response toResponse(final Exception exception) {
        LOG.error("Exception: ", exception);
        Error error = new ErrorBuilder()
                .setErrorMessage(exception.getMessage())
                .setCause(exception.getCause()).build();
        return Response.status(DEFAULT_STATUS_ERROR_CODE).entity(error).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
