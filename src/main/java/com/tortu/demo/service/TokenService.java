package com.tortu.demo.service;

import com.tortu.demo.model.Token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface TokenService {


    static final int TOKEN_DURATION_MINUTES = 5;

    /**
     * Creates a new token
     * @return token created
     */
    Token createToken();

    /**
     * Validates if there is a token with the id sent and if the token is still valid
     * @param tokenId id of the token to verify
     */
    void validateToken(String tokenId);

    /**
     * Updates the creationDate of the token to refresh its duration
     * @param tokenId the id of the token to refresh
     * @return the new valid token
     */
    Token refreshToken(String tokenId);


}
