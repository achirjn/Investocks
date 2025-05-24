package com.investocks.configurations;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.investocks.entities.Providers;
import com.investocks.entities.User;
import com.investocks.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler{
    
    @Autowired
    UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        System.out.println("=== OAuth2 SUCCESS HANDLER CALLED ===");
        
        try {
            DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
            System.out.println("User attributes: " + user.getAttributes());
            
            // Safely get attributes
            String name = user.getAttribute("name") != null ? user.getAttribute("name").toString() : "Unknown";
            String email = user.getAttribute("email") != null ? user.getAttribute("email").toString() : null;
            
            System.out.println("Extracted - Name: " + name + ", Email: " + email);
            
            if (email == null || email.isEmpty()) {
                System.err.println("ERROR: Email is null or empty");
                response.sendRedirect("/login?error=no_email");
                return;
            }

            // Check if user exists
            System.out.println("Checking if user exists with email: " + email);
            Optional<User> existingUser = userRepo.findByEmail(email);
            
            if (existingUser.isEmpty()) {
                System.out.println("Creating new user...");
                User newUser = new User();
                newUser.setUserName(name);
                newUser.setPassword("oauth2_user");
                newUser.setEmail(email);
                newUser.setProvider(Providers.Google);
                
                User savedUser = userRepo.save(newUser);
                System.out.println("Created new user with ID: " + savedUser.getId());
            } else {
                System.out.println("User already exists: " + existingUser.get().getId());
            }

            System.out.println("Redirecting to /user/welcome");
            new DefaultRedirectStrategy().sendRedirect(request, response, "/user/welcome");
            
        } catch (Exception e) {
            System.err.println("ERROR in OAuth2 success handler: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("/login?error=processing_failed");
        }
    }
}
