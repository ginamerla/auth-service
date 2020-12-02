package com.tortu.demo.service.implementation;

import com.tortu.demo.model.Token;
import com.tortu.demo.service.TokenService;
import lombok.extern.log4j.Log4j2;
import org.apache.cxf.common.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotAuthorizedException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
@Service
@Log4j2
public class TokenServiceMemoryBasedImplementation implements TokenService {

    private Map<String, Token> tokenMap = new ConcurrentHashMap<>();

    public Token createToken(){
        Token token = Token.builder()
                .tokenId(createUniqueId())
                .creationDate(LocalDateTime.now())
                .durationMinutes(TOKEN_DURATION_MINUTES).build();

        tokenMap.put(token.getTokenId(), token);
        log.info("Token created: {}", token);
        return token;
    }

    public void validateToken(String tokenId){
        if(StringUtils.isEmpty(tokenId)){
            log.error("token id is null/empty");
            throw new IllegalArgumentException("Token id not provided");
        }
        if(tokenMap.containsKey(tokenId)){
            log.info("Token found");
            Token token = tokenMap.get(tokenId);
            LocalDateTime expiration = getExpirationDate(token.getCreationDate());
            log.info("Checking expiration: "+expiration);
            if(LocalDateTime.now().isAfter(expiration)){
                log.error("token is invalid");
                throw new NotAuthorizedException("Token is invalid");
            }
            log.info("Token is valid");
        }else {
            log.error("token does not exist");
            throw new NoSuchElementException("Token does not exist");
        }
    }

    public Token refreshToken(String tokenId){
        if(StringUtils.isEmpty(tokenId)){
            log.error("tokenId is null/empty");
            throw new IllegalArgumentException("Token id not provided");
        }
        if(!tokenMap.containsKey(tokenId)){
            log.error("Token invalid");
            throw new NoSuchElementException("Token does not exist");
        }
        Token token = tokenMap.get(tokenId);
        log.info("Token found");
        token.setCreationDate(LocalDateTime.now());
        tokenMap.replace(tokenId,token);
        log.info("Token updated");
        return token;
    }

    /**
     * Creates an unique id for the token
     * @return the uuid created
     */
    private String createUniqueId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * Gets the expiration date of a given date
     * @param creationDate starting date
     * @return expiration date
     */
    private LocalDateTime getExpirationDate(LocalDateTime creationDate){
        LocalDateTime expirationDate = creationDate.plusMinutes(TOKEN_DURATION_MINUTES);
        return expirationDate;
    }

    /**
     * Returns the map of all the tokens created
     * @return token map
     */
    public Map<String, Token> getAllTokens(){
        return  tokenMap;
    }

    /**
     * Deletes all the expired tokens in the map
     */
    public void deleteExpiredTokens(){
        log.info("Deleting all expired tokens...");
        for (Map.Entry<String, Token> e: tokenMap.entrySet()){
            Token token = e.getValue();
            if(LocalDateTime.now().isAfter(getExpirationDate(token.getCreationDate()))){
                tokenMap.remove(token.getTokenId());
            }
        }
    }

    /**
     * Deletes all the expired tokens in the map
     */
    public void deleteAllTokens(){
        log.info("Deleting all tokens...");
        tokenMap.clear();
    }

    /**
     * Gets the token object with the id sent
     * @param tokenId id of the token to get
     * @return token object with the id sent
     */
    public Token getTokenbyId(String tokenId){
        log.info("Getting token with id:{}", tokenId);
        Token token = tokenMap.get(tokenId);
        if(token==null){
            log.error("No token found with id:{}", tokenId);
            throw new NoSuchElementException("No token found");
        }
        return token;
    }











//    public static void main (String[] arg){
//        TokenServiceMemoryBasedImplementation x = new TokenServiceMemoryBasedImplementation();
//
//        Calendar creationDate = Calendar.getInstance();
//        creationDate.set(2020,9,5,17,2,00);
//
//        Token token = new Token();
//        token.setDurationMinutes(3);
//        token.setCreationDate(creationDate.getTime());
//        token.setTokenId(x.createUniqueId());
//        x.print("creation date: "+creationDate.getTime());
//
//        x.tokenMap.put(token.getTokenId(), token);
//        x.printMap();
//
//        //      CREATE NEW TOKEN
////        Token token = x.createToken();
////        x.print(token.getTokenId());
////        x.print(token.getCreationDate().toString());
////        x.print(token.getDurationMinutes()+" minutes");
//
//        // VALIDATE TOKEN
//        x.print("validate token");
//        try{
//            x.validateToken(token);
//        }catch (Exception e){
//            x.print("ERROR: "+e.getMessage());
//        }
//        // REFRESH TOKEN
//        x.print("refreshing token");
//        Token newToken = x.refreshToken(token);
//        x.printMap();
//
//
//    }
//    private void print(String message){
//        System.out.println(message);
//    }
//    private void printMap(){
//        tokenMap.entrySet().forEach(e -> print(e.getKey()+" "+e.getValue()));
//    }
//
//    public static void main (String...args){
//        TokenServiceMemoryBasedImplementation x = new TokenServiceMemoryBasedImplementation();
//        LocalDateTime time = LocalDateTime.now();
//        LocalDateTime exp =x.getExpirationDate(time,TOKEN_DURATION_MINUTES);
//        System.out.println("time: "+time+"  -  expiration: "+exp);
//
//    }
}
