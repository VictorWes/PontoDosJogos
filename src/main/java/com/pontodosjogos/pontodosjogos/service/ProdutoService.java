package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import com.pontodosjogos.pontodosjogos.mapper.ProdutoMapper;
import com.pontodosjogos.pontodosjogos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoResponse criarProduto(ProdutoRequest produto) {
        Produto novoProduto = produtoRepository.save(produtoMapper.toEntity(produto));
        return produtoMapper.toResponseDTO(novoProduto);
    }




}
