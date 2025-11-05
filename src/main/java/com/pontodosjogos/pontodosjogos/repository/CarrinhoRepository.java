package com.pontodosjogos.pontodosjogos.repository;

import com.pontodosjogos.pontodosjogos.entity.Carrinho;
import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

    @Query("SELECT c FROM Carrinho c LEFT JOIN FETCH c.itens WHERE c.usuario.id = :usuarioId AND c.statusCarrinho = :status")
    Optional<Carrinho> findByUsuarioIdAndStatusCarrinho(Long usuarioId, StatusCarrinho status);

}
