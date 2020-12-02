package com.tortu.demo.rest.resources;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class TokenResource {
    private String tokenId;
    private LocalDateTime creationDate;
    private int  durationMinutes;
}
