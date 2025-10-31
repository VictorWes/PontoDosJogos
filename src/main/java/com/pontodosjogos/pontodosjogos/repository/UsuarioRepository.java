package com.pontodosjogos.pontodosjogos.repository;

import com.pontodosjogos.pontodosjogos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<UserDetails> findUserByEmail(String email);

}
