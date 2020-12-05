package com.tortu.demo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tortu.demo.exception.ExceptionMapperImplementation;
import com.tortu.demo.rest.AuthorizationV1RestService;
import com.tortu.demo.rest.AuthorizationV2RestService;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.asm.Advice;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

/**
 * Configuracion de JaxRS y todos los beans a exponer como REST Services
 */
@Order(1)
@Configuration
@ImportResource({ "classpath:META-INF/cxf/cxf.xml" })
@PropertySource({ "classpath:application.properties" })
@Log4j2
public class JaxRsServerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AuthorizationV1App authorizationV1App;

    @Autowired
    private AuthorizationV1RestService authorizationV1RestService;

    @Autowired
    private AuthorizationV2RestService authorizationV2RestService;

    @Autowired
    private ExceptionMapperImplementation exceptionMapper;

    @Autowired
    private LoggingResponseFilter responseFilter;
    @Autowired
    private LoggingRequestFilter requestFilter;

    /**
     * Default Jackson ObjectMapper.
     *
     * @return ObjectMapper bean.
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    /**
     * Default Jackson JSON Provider.
     *
     * @return JacksonJsonProvider bean.
     */
    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider(objectMapper());
    }
    @Bean
    public LoggingRequestFilter requestFilter(){
        return new LoggingRequestFilter();
    }
    @Bean
    public LoggingResponseFilter responseFilter(){
        return new LoggingResponseFilter();
    }
    /**
     * The Server bean which defines which controllers and providers are used by
     * this service.
     *
     * @return Server bean.
     */
    @Bean
    public Server jaxRsServer() {
        final Bus bus = (Bus) applicationContext.getBean(Bus.DEFAULT_BUS_ID);

        final JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
        factory.setApplication(authorizationV1App);
        factory.setServiceBeans(Arrays.<Object> asList(
                authorizationV1RestService,
                authorizationV2RestService
            ));
        factory.setAddress(factory.getAddress());
        factory.setProviders(Arrays.<Object> asList(jsonProvider(), exceptionMapper, responseFilter, responseFilter));
        factory.setBus(bus);
        log.info("JaxRsServerConfig : jaxRsServer bean created");
        return factory.create();
    }

}