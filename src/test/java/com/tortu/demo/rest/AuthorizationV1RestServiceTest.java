package com.tortu.demo.rest;

import com.tortu.demo.exception.GeneralException;
import com.tortu.demo.model.Token;
import com.tortu.demo.rest.mapper.TokenResourceMapper;
import com.tortu.demo.rest.resources.TokenResource;
import com.tortu.demo.service.implementation.TokenServiceMemoryBasedImplementation;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationV1RestServiceTest {

    @InjectMocks
    private AuthorizationV1RestService authRestService;
    @Mock
    private TokenServiceMemoryBasedImplementation tokenService;
    @Mock
    private TokenResourceMapper mapper;

    private static final int MINUTES_TEST = 3;
    private static final String TOKEN_ID_VALID = "abc";
    private static final String TOKEN_ID_INVALID = "123xyz";
    private static final LocalDateTime CREATION_DATE= LocalDateTime.now();

    @Test
    public void createToken() {
        Token newToken = Token.builder()
                .creationDate(CREATION_DATE)
                .durationMinutes(MINUTES_TEST)
                .tokenId(TOKEN_ID_VALID).build();
        TokenResource resource = TokenResource.builder()
                .creationDate(CREATION_DATE)
                .tokenId(TOKEN_ID_VALID)
                .durationMinutes(MINUTES_TEST).build();
        Mockito.when(tokenService.createToken()).thenReturn(newToken);
        Mockito.when(mapper.map(newToken)).thenReturn(resource);
        Response response = authRestService.createToken();
        Mockito.verify(tokenService, Mockito.times(1)).createToken();
        Mockito.verify(mapper,Mockito.times(1)).map(newToken);
        Assert.assertEquals(200, response.getStatus());
        Assert.assertNotNull(resource);
        Assert.assertEquals(resource.getCreationDate(), newToken.getCreationDate());
        Assert.assertNotNull(resource.getTokenId());
    }

    @Test
    public void verifyToken() {
        Token token = Token.builder()
                .creationDate(CREATION_DATE)
                .durationMinutes(MINUTES_TEST)
                .tokenId(TOKEN_ID_VALID).build();
        Mockito.when(tokenService.validateToken(TOKEN_ID_VALID)).thenReturn(token);
//        Mockito.doNothing().when(tokenService).validateToken(TOKEN_ID_VALID);
        Response response = authRestService.verifyToken(TOKEN_ID_VALID);
        Mockito.verify(tokenService,Mockito.times(1)).validateToken(TOKEN_ID_VALID);
        Assert.assertEquals(200, response.getStatus());
    }
    @Test(expected = IllegalArgumentException.class)
    public void verifyTokenInvalid() {
        Mockito.doThrow(new IllegalArgumentException()).when(tokenService).validateToken(TOKEN_ID_INVALID);
        Response response = authRestService.verifyToken(TOKEN_ID_INVALID);
        Mockito.verify(tokenService,Mockito.times(1)).validateToken(TOKEN_ID_INVALID);
    }
    @Test(expected = GeneralException.class)
    public void verifyTokenEmpty() {
        authRestService.verifyToken("");
    }

    @Test(expected = GeneralException.class)
    public void verifyTokenNull() {
        authRestService.verifyToken(null);
    }

    @Test
    public void updateToken() {
        Token newToken = Token.builder()
                .creationDate(CREATION_DATE)
                .durationMinutes(MINUTES_TEST)
                .tokenId(TOKEN_ID_VALID).build();
        Mockito.when(tokenService.refreshToken(TOKEN_ID_VALID)).thenReturn(newToken);
        Mockito.when(mapper.map(newToken)).thenReturn(Mockito.mock(TokenResource.class));
        Response response = authRestService.updateToken(TOKEN_ID_VALID);
        Mockito.verify(tokenService,Mockito.times(1)).refreshToken(TOKEN_ID_VALID);
        Mockito.verify(mapper,Mockito.times(1)).map(newToken);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test
    public void updateTokenNotExpired() {
        Token newToken = Token.builder()
                .creationDate(CREATION_DATE)
                .durationMinutes(MINUTES_TEST)
                .tokenId(TOKEN_ID_VALID).build();
        Mockito.when(tokenService.refreshToken(TOKEN_ID_VALID)).thenReturn(newToken);
        Mockito.when(mapper.map(newToken)).thenReturn(Mockito.mock(TokenResource.class));
        Response response = authRestService.updateToken(TOKEN_ID_VALID);
        Mockito.verify(tokenService,Mockito.times(1)).refreshToken(TOKEN_ID_VALID);
        Mockito.verify(mapper,Mockito.times(1)).map(newToken);
        Assert.assertEquals(200, response.getStatus());
    }

    @Test(expected = NoSuchElementException.class)
    public void updateTokenInvalid() {
        Mockito.doThrow(NoSuchElementException.class).when(tokenService).refreshToken(TOKEN_ID_INVALID);
        Response response = authRestService.updateToken(TOKEN_ID_INVALID);
        Mockito.verify(tokenService,Mockito.times(1)).refreshToken(TOKEN_ID_INVALID);
    }

    @Test(expected = GeneralException.class)
    public void updateTokenEmpty() {
        Response response = authRestService.updateToken("");
    }

    @Test(expected = GeneralException.class)
    public void updateTokenNull() {
        Response response = authRestService.updateToken(null);
    }

    @Test
    public void getAllTokens() {
    }
}