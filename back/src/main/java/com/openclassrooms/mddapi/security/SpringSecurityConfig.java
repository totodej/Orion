package com.openclassrooms.mddapi.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	private final JwtRequestFilter jwtRequestFilter;
	
	public SpringSecurityConfig(JwtRequestFilter jwtRequestFilter) {
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	/*
     Configure la sécurité HTTP :
     - Désactive CSRF, formLogin et httpBasic
     - Permet les routes /login et /register sans authentification
     - Toutes les autres requêtes nécessitent une authentification
     - Applique le filtrage JWT pour toutes les requêtes
     - Définit la session comme stateless
    */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowCredentials(true);
            config.addAllowedOrigin("http://localhost:4200");
            config.addAllowedHeader("*");
            config.addAllowedMethod("*");
            return config;
        }))
		.csrf(csrf -> csrf.disable())
		.formLogin(form -> form.disable())
		.httpBasic(basic -> basic.disable())
		.authorizeHttpRequests(auth -> auth
       		.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()
       		.anyRequest().authenticated())
       
       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	    	
	    return http.build();
	}
	
	/*
	 Bean pour encoder les mots de passe avec BCrypt
	*/
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	/*
	 Configuration CORS pour autoriser les requêtes depuis le front-end Angular
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
	
}
