package com.investocks.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.investocks.services.implementations.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
        .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/register", "/do-register", "/login", "/resources/**", "/static/**", "/css/**", "/js/**","/images/**").permitAll();
            authorize.requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        })
        .formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.defaultSuccessUrl("/user/welcome");  // Changed from successForwardUrl
            formLogin.usernameParameter("email");//since using email for identification of the user
            formLogin.passwordParameter("password");
            formLogin.permitAll();  // Added to ensure login page is accessible
        })
        .csrf(AbstractHttpConfigurer::disable) // Temporarily disable CSRF for troubleshooting
        .logout(logout ->{
            // logout.permitAll();
            logout.logoutUrl("/logout");
            logout.logoutSuccessUrl("/login?logout=true");
        })
        .oauth2Login(oauth->{
            oauth.loginPage("/login");
            // oauth.defaultSuccessUrl("/user/welcome", true);
            oauth.successHandler(handler);
            oauth.failureHandler((request, response, exception) -> {
                System.err.println("OAuth2 failure: " + exception.getMessage());
                exception.printStackTrace();
                response.sendRedirect("/login?error=oauth2_failed");
            });

        });

        return httpSecurity.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
