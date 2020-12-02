package com.tortu.demo.rest.resources;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HealthCheckResource {
    private String env;
    private String message;
}
