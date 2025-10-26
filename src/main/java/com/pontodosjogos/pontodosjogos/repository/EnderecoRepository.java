package com.pontodosjogos.pontodosjogos.repository;

import com.pontodosjogos.pontodosjogos.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByUsuarioId(Long usuarioId);

    /**
     * Encontra um Endereco específico, garantindo que ele pertence ao Usuario correto.
     * Necessário para métodos de segurança como 'atualizarEndereco' ou 'removerEndereco'.
     */
    Optional<Endereco> findByIdAndUsuarioId(Long id, Long usuarioId);

}
