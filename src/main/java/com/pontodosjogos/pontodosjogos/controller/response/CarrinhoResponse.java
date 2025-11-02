package com.pontodosjogos.pontodosjogos.controller.response;

import com.pontodosjogos.pontodosjogos.enums.StatusCarrinho;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record CarrinhoResponse(
        // Identificador do Carrinho
        Long id,

        // Data de criação (uso LocalDateTime para sincronizar com a entidade)
        LocalDateTime dataCriacao,

        // Status do Carrinho (ex: ATIVO, CONCLUIDO)
        StatusCarrinho status,

        // Lista de Itens no Carrinho (mapeada para o DTO de ItemCarrinho)
        List<ItemCarrinhoResponse> itens,

        // Valor total do carrinho (CALCULADO no Service)
        BigDecimal totalCarrinho
) {
}
