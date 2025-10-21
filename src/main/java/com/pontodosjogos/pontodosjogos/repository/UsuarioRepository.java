package com.pontodosjogos.pontodosjogos.repository;

import com.pontodosjogos.pontodosjogos.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
