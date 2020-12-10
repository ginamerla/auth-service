package com.tortu.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Log4j2
@Provider
@Priority(2)
public class LoggingResponseFilter implements ContainerResponseFilter{

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        MultivaluedMap<String, String> headerNames = containerResponseContext.getStringHeaders();
        StringBuilder str = new StringBuilder();
        str.append("Response Status: ");
        str.append(containerResponseContext.getStatus());
        str.append(" Headers: ");
        str.append(objectMapper.writeValueAsString(headerNames));
        str.append(" Payload: ");
        str.append(objectMapper.writeValueAsString(containerResponseContext.getEntity()));
        log.info(str.toString());
    }
}
