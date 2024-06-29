package com.cavdarfurkan.librarymanagement.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getContextPath();
        String redirectUrl;

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMINISTRATOR"))) {
            redirectUrl = "/admin/home";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_LIBRARIAN"))) {
            redirectUrl = "/librarian/return-books";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PUBLISHER"))) {
            redirectUrl = "/publisher/published-books";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_READER"))) {
            redirectUrl = "/reader/borrowed-books";
        } else {
            redirectUrl = "/";
        }

        response.sendRedirect(redirectUrl);
    }
}
