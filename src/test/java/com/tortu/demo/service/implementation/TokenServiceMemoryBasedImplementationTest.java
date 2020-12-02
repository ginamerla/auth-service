package com.tortu.demo.service.implementation;

import com.tortu.demo.model.Token;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.ws.rs.NotAuthorizedException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Log4j2
@RunWith(MockitoJUnitRunner.class)
public class TokenServiceMemoryBasedImplementationTest {

    static final String ID_TOKEN_VALID = "1tokenValid";
    static final String ID_TOKEN_VALID_DATE_EXPIRED = "2tokenValid";
    static final String ID_TOKEN_INVALID = "wrongTokenId";
    static final LocalDateTime TOKEN_CREATION_DATE_VALID = LocalDateTime.now().minusMinutes(2);
    static final LocalDateTime TOKEN_CREATION_DATE_INVALID = LocalDateTime.of(2020,1,1,12,0);
    static final int TOKEN_DURATION_MINUTES = 3;

    @InjectMocks
    public TokenServiceMemoryBasedImplementation tokenService = new TokenServiceMemoryBasedImplementation();

    public ConcurrentHashMap<String, Token> tokenMap;

    @Before
    public void init(){
        tokenMap = new ConcurrentHashMap<>();

//        Token tokenValid = new Token();
//        tokenValid.set(ID_TOKEN_VALID);
//        tokenValid.setCreationDate(TOKEN_CREATION_DATE_VALID);
//        tokenValid.setDurationMinutes(TOKEN_DURATION_MINUTES);
//        tokenMap.put(ID_TOKEN_VALID,tokenValid);
        Token tokenValid = Token.builder()
                .tokenId(ID_TOKEN_VALID)
                .creationDate(TOKEN_CREATION_DATE_VALID)
                .durationMinutes(TOKEN_DURATION_MINUTES).build();
        tokenMap.put(ID_TOKEN_VALID, tokenValid);

//        Token tokenValidDateExpired = new Token();
//        tokenValidDateExpired.setTokenId(ID_TOKEN_VALID_DATE_EXPIRED);
//        tokenValidDateExpired.setCreationDate(TOKEN_CREATION_DATE_INVALID);
//        tokenValidDateExpired.setDurationMinutes(TOKEN_DURATION_MINUTES);
        Token tokenValidDateExpired = Token.builder()
                .tokenId(ID_TOKEN_VALID_DATE_EXPIRED)
                .creationDate(TOKEN_CREATION_DATE_INVALID)
                .durationMinutes(TOKEN_DURATION_MINUTES).build();
        tokenMap.put(ID_TOKEN_VALID_DATE_EXPIRED,tokenValidDateExpired);
    }

    @Test
    public void createToken() {
        Token expected = tokenService.createToken();

        Assert.assertNotNull(expected);
        Assert.assertNotNull(expected.getTokenId());
        Assert.assertNotNull(expected.getCreationDate());
        Assert.assertTrue(expected.getCreationDate().isBefore(LocalDateTime.now()));
    }

    @Test
    public void validateToken() {
        log.debug("validating token");
        log.debug(String.valueOf(tokenMap.size()));
        ReflectionTestUtils.setField(tokenService,"tokenMap", tokenMap);

        tokenService.validateToken(ID_TOKEN_VALID);
    }

    @Test(expected = NoSuchElementException.class)
    public void validateTokenNotFound() {
        ReflectionTestUtils.setField(tokenService,"tokenMap", tokenMap);
        tokenService.validateToken(ID_TOKEN_INVALID);
    }

    @Test(expected = NotAuthorizedException.class)
    public void validateTokenInvalid() {
        ReflectionTestUtils.setField(tokenService,"tokenMap", tokenMap);
        tokenService.validateToken(ID_TOKEN_VALID_DATE_EXPIRED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateTokenIdNull() {
        ReflectionTestUtils.setField(tokenService,"tokenMap", tokenMap);
        tokenService.validateToken(null);
    }

    @Test
    public void refreshToken() {
        ReflectionTestUtils.setField(tokenService, "tokenMap",tokenMap);
        Token expectedToken = tokenService.refreshToken(ID_TOKEN_VALID_DATE_EXPIRED);

        Assert.assertTrue(expectedToken.getCreationDate().plusMinutes(TOKEN_DURATION_MINUTES).isAfter(LocalDateTime.now()));
    }

    @Test(expected = NoSuchElementException.class)
    public void refreshTokenInvalid() {
        ReflectionTestUtils.setField(tokenService, "tokenMap",tokenMap);
        tokenService.refreshToken(ID_TOKEN_INVALID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void refreshTokenIdNull() {
        ReflectionTestUtils.setField(tokenService, "tokenMap",tokenMap);
        tokenService.refreshToken(null);
    }

    @Test
    public void refreshTokenValid(){
        ReflectionTestUtils.setField(tokenService, "tokenMap", tokenMap);
        Token token = tokenMap.get(ID_TOKEN_VALID);
        LocalDateTime before = token.getCreationDate();
        log.debug("Time before: "+before);

        token = tokenService.refreshToken(ID_TOKEN_VALID);
        log.debug("Time after: "+token.getCreationDate());

        Assert.assertTrue(before.isBefore(token.getCreationDate()));
    }
}