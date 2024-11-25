package itst.socialraccoon.api;

import itst.socialraccoon.api.models.AuthenticationModel;
import itst.socialraccoon.api.services.AuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            http
                    .csrf(csrf -> csrf.disable()) // Disable CSRF protection (optional, but often needed for API)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/authentications/recover", "/authentications/change", "/signin", "/signup").permitAll()
                            .requestMatchers(HttpMethod.GET, "/careers", "/careers/").permitAll()
                            .requestMatchers(HttpMethod.POST, "/users/withImage",  "/users").permitAll()
                            .requestMatchers(HttpMethod.GET, "/").authenticated()
                            .requestMatchers(HttpMethod.POST, "/").authenticated()
                            .requestMatchers(HttpMethod.PUT, "/").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/").authenticated()
                            .anyRequest().authenticated())
                    .httpBasic(Customizer.withDefaults()) // Use HTTP Basic authentication
                    .formLogin(withDefaults())
                    .rememberMe(withDefaults())
                    .logout(logout -> logout.logoutUrl("/signout").permitAll());

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException("Error building security filter chain", e);
        }
    }

    @Bean
    public UserDetailsService userDetailsService(AuthenticationService authenticationService) {
        return email -> {
            AuthenticationModel authentication = authenticationService.findByEmail(email);
            if (authentication == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            return User.withUsername(authentication.getEmail())
                    .password(authentication.getPassword())
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns("http://localhost:3000", "http://localhost:4200", "http://localhost:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
