package com.tortu.demo.rest;

import com.tortu.demo.rest.resources.HealthCheckResource;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Service
@Path("/v2/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationV2RestService {

    @GET
    @Path("/health-check")
    public Response healthCheck(){
        HealthCheckResource resource = HealthCheckResource.builder()
                .env("PROD")
                .message("SUCCESS")
                .build();
        return Response.ok(resource).build();
    }
}
