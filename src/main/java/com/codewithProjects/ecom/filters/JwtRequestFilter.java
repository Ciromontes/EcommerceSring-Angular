package com.codewithProjects.ecom.filters;

import com.codewithProjects.ecom.services.jwt.UserDetailsServiceImpl;
import com.codewithProjects.ecom.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Anotación para indicar que esta clase es un componente de Spring
@Component
// Anotación para generar automáticamente un constructor con los campos finales como argumentos
@RequiredArgsConstructor
// Esta clase extiende OncePerRequestFilter, un filtro de Spring que se ejecuta una vez por cada solicitud HTTP
public class JwtRequestFilter extends OncePerRequestFilter {
    // Inyección de dependencias de Spring
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;

    // Método que se ejecuta una vez por cada solicitud HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        // Obtiene el encabezado de autorización de la solicitud HTTP
        String authHeader = request.getHeader("Authorization");

        // Inicializa el token y el nombre de usuario como nulos
        String token = null;
        String username = null;

        // Si el encabezado de autorización no es nulo y comienza con "Bearer ",
        // extrae el token y el nombre de usuario
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username= jwtUtil.extractUserName(token);
        }
        // Si el nombre de usuario no es nulo y no hay
        // una autenticación en el contexto de seguridad, valida el token
        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= userDetailsService.loadUserByUsername(username);

            // Si el token es válido, crea una autenticación y la establece en el contexto de seguridad
            if(jwtUtil.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken. setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        // Continúa con el siguiente filtro en la cadena
        filterChain.doFilter(request, response);

    }
}
