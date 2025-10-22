package com.pontodosjogos.pontodosjogos.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenService {

    @Value("${desafio.security.secret}")
    private String secret;

    public String generateToken(Usuario usuario){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(usuario.getEmail())
                .withClaim("userId", usuario.getId())
                .withClaim("userName", usuario.getNome())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("PontoDosJogos")
                .sign(algorithm);
    }

    public Optional<JWTUserData> verifyToken(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            DecodedJWT jwt = JWT.require(algorithm)
                    .build()
                    .verify(token);

            return Optional.of(JWTUserData
                    .builder()
                    .id(jwt.getClaim("userId").asLong())
                    .email(jwt.getSubject())
                    .build());

        }catch (JWTVerificationException exception){
            return Optional.empty();
        }

    }
}
