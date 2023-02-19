package com.lambo.api.auth;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.regex.Pattern;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTHORIZATION_HEADER = "x-api-key";
    private final static Pattern SWAGGER_PATH_PATTERN = Pattern.compile("(/api/docs)|(/api/swagger-ui/.*)|(/v3/api-docs/.*)");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        if (request.getRequestURI().equals("/api/login") || SWAGGER_PATH_PATTERN.matcher(request.getRequestURI()).matches()) {
            return true;
        }

        if (authorizationHeader == null || !authorizationHeader.equals("secret")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }

        return true;
    }
}
