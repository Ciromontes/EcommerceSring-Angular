package com.codeWithProjects.ecom.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
// Anotación para indicar que esta clase es un componente de Spring
@Component
public class JwtUtil {
    // Constante para la clave secreta utilizada para firmar los tokens JWT
    public static final String SECRET = "413F4428472B4B6250655368566D5970337336763979244226452948404D6351";

    // Método para generar un token JWT para un nombre de usuario dado
    public String generateToken(String userName){
        // Crea un mapa vacío para las reclamaciones del token
        Map<String, Object> claims = new HashMap<>();
        // Llama al método createToken para crear el token
        return createToken(claims, userName);
    }

    // Método para crear un token JWT
    private String createToken(Map<String, Object> claims, String userName){
        // Construye el token con las reclamaciones,
        // el nombre de usuario, la fecha de emisión, la fecha de vencimiento y la firma
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    // Método para obtener la clave de firma para el token
    private Key getSignKey(){
        // Decodifica la clave secreta y la convierte en una clave HMAC SHA
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Método para extraer el nombre de usuario de un token
    public String extractUserName(String token){
        // Llama al método extractClaim para extraer el nombre de usuario del token
        return extractClaim(token, Claims::getSubject);
    }

    // Método para extraer una reclamación específica de un token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        // Extrae todas las reclamaciones del token
        final Claims claims = extractAllClaims(token);
        // Aplica la función de resolución de reclamaciones para extraer una reclamación específica
        return claimsResolver.apply(claims);
    }

    // Método para extraer todas las reclamaciones de un token
    private Claims extractAllClaims(String token){
        // Parsea el token y extrae todas las reclamaciones
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    // Método para verificar si un token ha expirado
    private Boolean isTokenExpired(String token){
        // Comprueba si la fecha de vencimiento del token es anterior a la fecha actual
        return extractExpiration(token).before(new Date());
    }

    // Método para extraer la fecha de vencimiento de un token
    public Date extractExpiration(String token){
        // Llama al método extractClaim para extraer la fecha de vencimiento del token
        return extractClaim(token, Claims::getExpiration);
    }

    // Método para validar un token
    public Boolean validateToken(String token, UserDetails userDetails) {
        // Extrae el nombre de usuario del token
        final String username = extractUserName(token);
        // Valida el token comprobando si el nombre de
        // usuario en el token coincide con el nombre de usuario en
        // los detalles del usuario y si el token no ha expirado
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
