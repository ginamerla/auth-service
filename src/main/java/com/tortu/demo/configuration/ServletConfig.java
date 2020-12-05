package com.tortu.demo.configuration;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
    /**
     * Replaces web.xml
     *
     * @param context
     *            the application context.
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean(ApplicationContext context) {
        ServletRegistrationBean bean = new ServletRegistrationBean(new CXFServlet(), "/api/*");
        bean.setLoadOnStartup(1);
        return bean;
    }

    /**
     * enables spring security filter.
     * @return FilterRegistrationBean
     */
//    @Bean
//    public FilterRegistrationBean<RequestResponseLoggingFilter> loginFilter() {
//        FilterRegistrationBean<RequestResponseLoggingFilter> registration = new FilterRegistrationBean<>();
//        registration.setFilter(new RequestResponseLoggingFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("loggingFilter");
//        registration.setOrder(1);
//        return registration;
//    }

}
