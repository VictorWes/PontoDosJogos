package com.pontodosjogos.pontodosjogos.controller;

import com.pontodosjogos.pontodosjogos.controller.request.ItemCarrinhoRequest;
import com.pontodosjogos.pontodosjogos.controller.response.CarrinhoResponse;
import com.pontodosjogos.pontodosjogos.entity.Usuario;
import com.pontodosjogos.pontodosjogos.exception.AccessDeniedException;
import com.pontodosjogos.pontodosjogos.service.CarrinhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinho")
@RequiredArgsConstructor
public class CarrinhoController {

    private final CarrinhoService carrinhoService;

    private Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // O SecurityFilter já garante que o Principal seja a Entidade Usuario
        if (authentication == null || !(authentication.getPrincipal() instanceof Usuario)) {
            throw new AccessDeniedException("Autenticação inválida. O principal não é um objeto de Usuário.");
        }
        return (Usuario) authentication.getPrincipal();
    }

    @GetMapping
    public ResponseEntity<CarrinhoResponse> visualizarCarrinho() {
        Usuario usuario = getUsuarioAutenticado();

        CarrinhoResponse carrinho = carrinhoService.getCarrinhoAtivoResponse(usuario.getId());

        return ResponseEntity.ok(carrinho);
    }

    @PostMapping("/item")
    public ResponseEntity<CarrinhoResponse> adicionarItem(@Valid @RequestBody ItemCarrinhoRequest request) {
        Usuario usuario = getUsuarioAutenticado();

        CarrinhoResponse carrinhoAtualizado = carrinhoService.adicionarItemAoCarrinho(usuario.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoAtualizado);
    }

    @DeleteMapping("/item/{itemCarrinhoId}")
    public ResponseEntity<Void> removerItem(@PathVariable Long itemCarrinhoId) {
        Usuario usuario = getUsuarioAutenticado();

        carrinhoService.removerItemDoCarrinho(usuario.getId(), itemCarrinhoId);

        // Retorna 204 No Content, indicando sucesso sem corpo de resposta
        return ResponseEntity.noContent().build();
    }
}
