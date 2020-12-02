package com.tortu.demo.configuration;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
@Log4j2
//@Component
//@Order(2)
public class TransactionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        log.info("Starting a transaction for request {}",req.getRequestURI());
//
        filterChain.doFilter(servletRequest,servletResponse);
//        log.info("Committing a transaction for request: {}", req.getRequestURI());
    }
}
