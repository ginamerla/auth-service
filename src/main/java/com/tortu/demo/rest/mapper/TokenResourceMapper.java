package com.tortu.demo.rest.mapper;

import com.tortu.demo.model.Token;
import com.tortu.demo.rest.resources.TokenResource;
import org.springframework.stereotype.Component;

/**
 * Assign model properties to the Rest resource
 */
@Component
public class TokenResourceMapper implements DefaultResourceMapper<Token, TokenResource>{

    @Override
    public TokenResource map(Token model) {
        if(model==null){
            return null;
        }
        TokenResource resource = TokenResource.builder()
                .creationDate(model.getCreationDate())
                .durationMinutes(model.getDurationMinutes())
                .tokenId(model.getTokenId()).build();
        return resource;
    }
}
