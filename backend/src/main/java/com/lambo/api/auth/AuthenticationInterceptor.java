package com.lambo.api.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "x-api-key";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        
        if (request.getMethod().equals("OPTIONS")) {
        	return true;
        } 
        
        if(request.getRequestURI().equals("/api/login")) {
        	return true;
        }
        
        if (!authorizationHeader.equals("secret")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }

        return true;
    }
}
