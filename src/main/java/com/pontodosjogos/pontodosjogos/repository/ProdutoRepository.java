package com.pontodosjogos.pontodosjogos.repository;

import com.pontodosjogos.pontodosjogos.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
