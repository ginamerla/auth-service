package com.tortu.demo.rest;

import com.tortu.demo.exception.GeneralException;
import com.tortu.demo.model.Token;
import com.tortu.demo.rest.mapper.TokenResourceMapper;
import com.tortu.demo.rest.resources.TokenResource;
import com.tortu.demo.service.TokenService;
import com.tortu.demo.service.implementation.TokenServiceMemoryBasedImplementation;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

@Log4j2
@Service
@Path("/v1/auth")
public class AuthorizationV1RestService {

    @Autowired
    private TokenServiceMemoryBasedImplementation tokenService;
    @Autowired
    private TokenResourceMapper mapper;

    @GET
    @Path("/health-check")
    public Response healthCheck(){
        return Response.ok("OK").build();
    }

    @GET
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createToken(){
        Token token = tokenService.createToken();
        TokenResource resource = mapper.map(token);
        return Response.ok(resource).build();
    }

    @GET
    @Path("/verify/{tokenid}")
    public Response verifyToken(@PathParam("tokenid")String tokenId){
        if(StringUtils.isEmpty(tokenId)){
            throw new GeneralException("Token Id is null");
        }
        tokenService.validateToken(tokenId);
        return Response.ok().build();
    }

    @PUT
    @Path("/update/{tokenid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateToken(@PathParam("tokenid")String tokenId){
        if(StringUtils.isEmpty(tokenId)){
            throw new GeneralException("Token id is null");
        }
        Token token = tokenService.refreshToken(tokenId);
        TokenResource resource = mapper.map(token);
        return Response.ok(resource).build();
    }


    @GET
    @Path("/get-all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTokens(){
        Map<String, Token> tokenMap = tokenService.getAllTokens();
        return Response.ok(tokenMap).build();
    }

    @DELETE
    @Path("/exp")
    public Response deleteExpired(){
        tokenService.deleteExpiredTokens();
        return Response.ok().build();
    }

    @DELETE
    @Path("/all")
    public Response deleteAll(){
        tokenService.deleteAllTokens();
        return Response.ok().build();
    }
    @GET
    @Path("/get/{tokenid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getToken(@PathParam("tokenid") String tokenId){
        Token token = tokenService.getTokenbyId(tokenId);
        TokenResource resource = mapper.map(token);
        return Response.ok(resource).build();
    }

}
