package com.tortu.demo.configuration;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Log4j2
public class RequestResponseLoggingFilter implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse.getCharacterEncoding() == null) {
            servletResponse.setCharacterEncoding("UTF-8"); // Or whatever default. UTF-8 is good for World Domination.
        }
        HttpServletResponseCopier responseCopier = new HttpServletResponseCopier((HttpServletResponse) servletResponse);
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String method = ((HttpServletRequest) servletRequest).getMethod();
        String uri = req.getRequestURI();
        String requestHeaders = getRequestHeaders(req);
        log.info("Request - Method:{} | URL:{} \n| Headers: {}", method, uri, requestHeaders);
        try {
            filterChain.doFilter(servletRequest, responseCopier);
            responseCopier.flushBuffer();
        } finally {
            byte[] copy = responseCopier.getCopy();
            String body = new String(copy, servletResponse.getCharacterEncoding());
            String responseHeaders = getResponseHeaders((HttpServletResponse)servletResponse);
            log.info("Response - Method:{} | URL:{} | Body: {} \n| Headers: {}",method,uri,body,responseHeaders);
        }
    }

    @Override
    public void destroy() {
        // NOOP.
    }

    public String getRequestHeaders(HttpServletRequest request){
        Enumeration<String> headerNames = request.getHeaderNames();
        StringBuilder str= new StringBuilder();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                str.append(name).append(": ");
                Enumeration<String> values = request.getHeaders(name);
                while(values.hasMoreElements()){
                    String value = values.nextElement();
                    str.append(value).append("\n");
                }
            }
        }
        return str.toString();
    }
    public String getResponseHeaders(HttpServletResponse response){
        Collection<String> headerNames = response.getHeaderNames();
        StringBuilder str= new StringBuilder();
        if (headerNames != null) {
            for(String name:headerNames){
                str.append(name).append(": ");
                Collection<String> values = response.getHeaders(name);
                for(String value:values){
                    str.append(value).append("\n");
                }
            }
        }
        return str.toString();
    }


}
