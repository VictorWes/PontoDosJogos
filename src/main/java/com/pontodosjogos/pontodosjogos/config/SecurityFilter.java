package com.pontodosjogos.pontodosjogos.config;

import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    // Método auxiliar para extrair o token do cabeçalho
    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring("Bearer ".length()).trim(); // trim() para remover espaços
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String uri = request.getRequestURI();

        // Permite que rotas públicas passem SEM processar o token (se for o caso)
        if (uri.endsWith("/api/auth/register") || uri.endsWith("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Tenta recuperar o token para rotas protegidas
        String token = recoverToken(request);

        if (token != null) {
            // 1. Verifica o Token e busca os dados de payload
            Optional<JWTUserData> optJwtUserData = tokenService.verifyToken(token);

            if (optJwtUserData.isPresent()) {
                JWTUserData userData = optJwtUserData.get();

                // 2. BUSCA O USUÁRIO REAL NO BANCO DE DADOS
                Optional<Usuario> optUsuario = usuarioRepository.findById(userData.id());

                if (optUsuario.isPresent()) {
                    Usuario usuario = optUsuario.get();

                    // 3. CRIAÇÃO E INJEÇÃO DA AUTENTICAÇÃO
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

                    // ISTO FAZ A MÁGICA: Define quem está logado para o Spring Security
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        // Continua a cadeia de filtros.
        // Se a autenticação falhou acima, o AuthorizationFilter irá bloquear com 401/403.
        filterChain.doFilter(request, response);
    }
}