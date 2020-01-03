package org.jarvis.kk.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jarvis.kk.domain.Token;
import org.jarvis.kk.repositories.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
/**
 * RestAuthenticationLogoutSuccessHandler
 */
@Component
@AllArgsConstructor
public class RestAuthenticationLogoutSuccessHandler implements LogoutSuccessHandler {
    
    private TokenRepository tokenRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
                String token = request.getParameter("token");
                tokenRepository.delete(Token.builder().token(token).build());                
    }



    
}