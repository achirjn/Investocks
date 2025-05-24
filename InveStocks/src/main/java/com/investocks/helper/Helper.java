package com.investocks.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication){
        // AuthenticationPrincipal principal = (AuthenticationPrincipal) authentication.getPrincipal();

        if(authentication instanceof OAuth2AuthenticationToken oauth2Token){
            // var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            // var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            // if(clientId.equalsIgnoreCase("google")){
            // }
            // else if(clientId.equalsIgnoreCase("github")){
            // }

            //since using oauth for only google
            
            OAuth2User oauth2User = oauth2Token.getPrincipal();
            // // Debug: Print all attributes
            // System.out.println("OAuth2 User attributes:");
            // oauth2User.getAttributes().forEach((key, value) -> {
            //     System.out.println("  " + key + " : " + value);
            // });
            String email = oauth2User.getAttribute("email");
            return email != null ? email : "";
        }
        return authentication.getName();
    }
}
