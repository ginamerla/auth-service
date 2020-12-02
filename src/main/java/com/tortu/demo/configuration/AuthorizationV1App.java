package com.tortu.demo.configuration;

import org.springframework.stereotype.Service;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Service
@ApplicationPath("v1")
public class AuthorizationV1App extends Application {

}
