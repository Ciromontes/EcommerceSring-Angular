package com.codewithProjects.ecom.controller;

import com.codewithProjects.ecom.dto.AuthenticationRequest;
import com.codewithProjects.ecom.dto.SignupRequest;
import com.codewithProjects.ecom.dto.UserDto;
import com.codewithProjects.ecom.entity.User;
import com.codewithProjects.ecom.repository.UserRepository;
import com.codewithProjects.ecom.services.auth.AuthService;;
import com.codewithProjects.ecom.utils.JwtUtil;
import com.codewithProjects.ecom.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;
// Anotación para indicar que esta clase es un controlador REST de Spring
@RestController
// Anotación para generar automáticamente un constructor con los campos finales como argumentos
@RequiredArgsConstructor
public class AuthController {
    // Inyección de dependencias de Spring
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // Constantes para el prefijo del token y el nombre del encabezado de autorización
    public static final String TOKEN_PREFIX= "Bearer";
    public static final String HEADER_STRING= "Authorization";

    private final AuthService authService;

    // Método para autenticar un usuario y generar un token JWT
    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
                                          HttpServletResponse response) throws IOException, JSONException {

        // Intenta autenticar al usuario con el nombre de usuario y la contraseña proporcionados
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));
            // Si la autenticación falla, lanza una excepción
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Incorrect username or password");
        }

        // Carga los detalles del usuario y busca al usuario en el repositorio
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        // Genera un token JWT para el usuario
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Si el usuario existe, escribe el ID y el rol del usuario en la respuesta y agrega el token JWT al encabezado de autorización
        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getId())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );
            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");
            response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + jwt);
        }
    }
    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("user already exist", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }
}
