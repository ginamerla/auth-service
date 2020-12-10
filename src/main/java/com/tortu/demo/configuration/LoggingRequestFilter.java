package com.tortu.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;


@Log4j2
@Provider
@PreMatching
@Priority(1)
public class LoggingRequestFilter implements ContainerRequestFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        StringBuilder str = new StringBuilder();
        str.append("Request URL: ");
        str.append(containerRequestContext.getUriInfo());
        str.append(" Method: ");
        str.append(containerRequestContext.getMethod());
        str.append(" Headers: ");
        MultivaluedMap<String, String> headerNames = containerRequestContext.getHeaders();
        str.append(objectMapper.writeValueAsString(headerNames));
        log.info(str.toString());
    }
}
