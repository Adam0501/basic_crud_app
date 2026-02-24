package hu.unideb.inf.books_backend;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class BooksBackendApplication
    extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BooksBackendApplication.class, args);
    }

    @Configuration
    public static class WebConfiguration
            implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE");
        }
    }

    @Configuration
    @AllArgsConstructor
    public static class SecurityConfiguration {

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationProvider authenticationProvider(
                @NonNull final PasswordEncoder passwordEncoder,
                @NonNull final UserDetailsService userDetailsService) {

            final DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
            daoAuthenticationProvider.setUserDetailsService(userDetailsService);
            daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
            return daoAuthenticationProvider;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            return http
                    .cors(Customizer.withDefaults())
                    .csrf(csrf -> csrf
                            .ignoringRequestMatchers(PathRequest.toH2Console())
                            .disable())
                    .authorizeHttpRequests(
                            (requests) -> requests
                                    .requestMatchers(PathRequest.toH2Console())
                                    .permitAll()
                                    .requestMatchers("/api/admin/**")
                                    .hasRole("ADMIN")
                                    .requestMatchers("/api/author/**")
                                    .hasRole("USER")
                                    .requestMatchers("/search")
                                    .hasAnyRole("USER", "ADMIN")
                                    .requestMatchers("/books/**")
                                    .hasAnyRole("USER", "ADMIN")
                                    .requestMatchers("/api/author/me")
                                    .hasAnyRole("USER", "ADMIN")
                                    .requestMatchers("/api/me")
                                    .hasAnyRole("USER", "ADMIN")
                                    .anyRequest()
                                    .authenticated()
                    )
                    .headers(configurer -> configurer.frameOptions(
                            HeadersConfigurer.FrameOptionsConfig::sameOrigin
                    ))
                    .httpBasic(Customizer.withDefaults())
                    .formLogin(AbstractHttpConfigurer::disable)
                    .build();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins(List.of("http://localhost:8082"));
            config.setAllowedMethods(List.of("GET","POST", "PUT", "DELETE"));
            config.setAllowedHeaders(List.of("Authorization","Content-Type"));
            config.setAllowCredentials(true);
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", config);
            return source;
        }
    }

}
