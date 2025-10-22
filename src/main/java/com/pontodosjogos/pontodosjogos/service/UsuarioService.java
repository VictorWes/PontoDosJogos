package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository userRepository;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Usuario saveUser(Usuario user){

        String password = user.getSenha();
        user.setSenha(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

}
