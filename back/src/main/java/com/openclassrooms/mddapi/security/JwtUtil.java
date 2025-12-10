package com.openclassrooms.mddapi.security;

import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    
    @Value("${jwt.expiration}")
    private long EXPIRATION_TIME;
	
    /*
     Génère un token JWT pour un utilisateur donné.
     Le token contient des claims avec les informations de l'utilisateur.
    */
	public String generateToken(User user) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("createdAt", user.getCreatedAt() != null ? user.getCreatedAt().format(formatter) : null)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
	
    /*
     Extrait les claims d'un token JWT valide.
    */
	public Claims extractClaims(String token) {
       return Jwts.parser()
               .setSigningKey(SECRET_KEY)
               .parseClaimsJws(token)
               .getBody();
	}
	
	/*
     Vérifie si un token JWT est valide (signature correcte et non expiré).
    */
	public boolean isTokenValid(String token) {
       try {
           Claims claims = extractClaims(token);
           return !claims.getExpiration().before(new Date());
       } catch (Exception e) {
           return false;
       }
	}
	
	public Integer extractUserId(String token) {
	    Claims claims = extractClaims(token);
	    return claims.get("userId", Integer.class);
	}
	
}
