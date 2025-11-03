package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UsuarioRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(username)
                // 3. Se o Optional estiver vazio, lança a exceção de UserDetails
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));
        // Nota: A mensagem de "Usuario ou senha invalido" é do seu AuthController
    }
}
