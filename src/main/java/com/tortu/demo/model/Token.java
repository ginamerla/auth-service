package com.tortu.demo.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class Token {
    private String tokenId;
    private LocalDateTime creationDate;
    private int  durationMinutes;
}
