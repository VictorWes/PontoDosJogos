package com.pontodosjogos.pontodosjogos.controller;


import com.pontodosjogos.pontodosjogos.config.TokenService;
import com.pontodosjogos.pontodosjogos.controller.request.LoginRequest;
import com.pontodosjogos.pontodosjogos.controller.request.UsuarioRequest;
import com.pontodosjogos.pontodosjogos.controller.response.LoginResponse;
import com.pontodosjogos.pontodosjogos.controller.response.UsuarioResponse;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.exception.UsernameOrPassWordInvalidException;
import com.pontodosjogos.pontodosjogos.mapper.UsuarioMapper;
import com.pontodosjogos.pontodosjogos.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // Endpoint base para operações de usuário
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request){

        Usuario savedUser = usuarioService.saveUser(usuarioMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toResponse(savedUser));

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
        try {

            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.password());
            Authentication authenticate = authenticationManager.authenticate(userAndPass);

            Usuario user = (Usuario) authenticate.getPrincipal();

            String token = tokenService.generateToken(user);

            return ResponseEntity.ok(new LoginResponse(token));

        }catch (BadCredentialsException e){
            throw new UsernameOrPassWordInvalidException("Usuario ou senha invalida");
        }
    }
}
