package com.pontodosjogos.pontodosjogos.service;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.entity.Produto;
import com.pontodosjogos.pontodosjogos.exception.RecursoNaoEncontradoException;
import com.pontodosjogos.pontodosjogos.mapper.ProdutoMapper;
import com.pontodosjogos.pontodosjogos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    @Transactional
    public ProdutoResponse criarProduto(ProdutoRequest produto) {
        Produto novoProduto = produtoRepository.save(produtoMapper.toEntity(produto));
        return produtoMapper.toResponseDTO(novoProduto);
    }

    @Transactional
    public List<ProdutoResponse> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();

        // Usa o método de mapeamento de lista do seu ProdutoMapper manual
        return produtos.stream()
                .map(produtoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProdutoResponse buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Produto com ID " + id + " não encontrado."));

        return produtoMapper.toResponseDTO(produto);
    }


}
