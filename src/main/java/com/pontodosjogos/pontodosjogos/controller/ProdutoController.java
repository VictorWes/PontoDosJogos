package com.pontodosjogos.pontodosjogos.controller;

import com.pontodosjogos.pontodosjogos.controller.request.ProdutoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.ProdutoResponse;
import com.pontodosjogos.pontodosjogos.service.ProdutoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;


    @PostMapping
    public ResponseEntity<ProdutoResponse> criarProduto(@RequestBody ProdutoRequest request) {
        ProdutoResponse response = produtoService.criarProduto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    

}
