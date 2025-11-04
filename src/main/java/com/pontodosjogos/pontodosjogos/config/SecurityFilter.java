package com.pontodosjogos.pontodosjogos.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    public SecurityFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String autorhizationHeader = request.getHeader("Authorization");


        if (Strings.isNotEmpty(autorhizationHeader) && autorhizationHeader.startsWith("Bearer")){
            String token = autorhizationHeader.substring("Bearer ".length());

            Optional<JWTUserData> optJwtUserData = tokenService.verifyToken(token);


            if (optJwtUserData.isPresent()){
                JWTUserData userData = optJwtUserData.get();

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userData, null,null);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }


            filterChain.doFilter(request,response);
        }else {
            filterChain.doFilter(request,response);
        }


    }

}
