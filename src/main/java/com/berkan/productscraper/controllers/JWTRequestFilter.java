package com.berkan.productscraper.controllers;

import com.berkan.productscraper.controllers.exceptions.UnAuthorizedException;
import com.berkan.productscraper.utility.JWTToken;
import com.berkan.productscraper.utility.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private WebConfig webConfig;

    private static final Set<String> SECURED_PATHS =
            Set.of("/products", "/brands");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();

        if (HttpMethod.OPTIONS.matches(request.getMethod()) || SECURED_PATHS.stream().noneMatch(servletPath::startsWith)) {

            filterChain.doFilter(request, response);
            return;
        }

        JWTToken jwToken = null;

        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (encryptedToken != null) {
            encryptedToken = encryptedToken.replace("Bearer ", "");
            jwToken = JWTToken.decode(encryptedToken, this.webConfig.passPhrase);
        }

        if (jwToken == null) {
            throw new UnAuthorizedException("You need to login");
        }

        request.setAttribute(JWTToken.getJwtUseridClaim(), jwToken);

        filterChain.doFilter(request, response);

    }
}
