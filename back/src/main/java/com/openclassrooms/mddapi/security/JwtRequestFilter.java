package com.openclassrooms.mddapi.security;

import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	
	public JwtRequestFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}
	
	/*
	 Filtre qui intercepte toutes les requêtes HTTP pour vérifier la présence
	 d'un token JWT dans l'en-tête Authorization.
	*/
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
           throws ServletException, IOException {
		
       final String authorizationHeader = request.getHeader("Authorization");

       String token = null;
       Claims claims = null;

       if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
           token = authorizationHeader.substring(7);
           
           if (jwtUtil.isTokenValid(token)) {
               claims = jwtUtil.extractClaims(token);
               
               JwtUser jwtUser = new JwtUser(
            		   claims.get("userId", Integer.class), 
            		   claims.get("name", String.class), 
            		   claims.get("email", String.class),
            		   claims.get("createdAt", String.class));
               
               UsernamePasswordAuthenticationToken authToken =
                       new UsernamePasswordAuthenticationToken(jwtUser, null, Collections.emptyList());
               
               authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authToken);
           }
       }
       
       chain.doFilter(request, response);
   }

}
