CREATE TABLE item_carrinho (

    -- Chave Primária
    id BIGSERIAL PRIMARY KEY,

    -- Chaves Estrangeiras
    carrinho_id BIGINT NOT NULL,  -- Referencia Carrinho (V4)
    produto_id BIGINT NOT NULL,   -- Referencia Produto (V3)

    -- Campos de Detalhe do Item
    quantidade INTEGER NOT NULL CHECK (quantidade > 0),

    -- Preço Imutável: Garante que o preço na hora da compra é registrado.
    preco_unitario_na_compra NUMERIC(10, 2) NOT NULL,

    -- Definição das Chaves Estrangeiras
    CONSTRAINT fk_item_carrinho_carrinho
        FOREIGN KEY (carrinho_id)
        REFERENCES carrinho (id)
        ON DELETE CASCADE, -- Se o carrinho for deletado, seus itens são removidos.

    CONSTRAINT fk_item_carrinho_produto
        FOREIGN KEY (produto_id)
        REFERENCES produtos (id)
        ON DELETE RESTRICT, -- RESTRICT é comum para produtos: não permite deletar um produto se ele estiver em pedidos/carrinhos ativos.

    -- Restrição de Unicidade: Um produto só pode aparecer uma vez por carrinho (a quantidade é somada)
    CONSTRAINT uk_carrinho_produto UNIQUE (carrinho_id, produto_id)
);

-- Índices
CREATE INDEX idx_item_carrinho_carrinho ON item_carrinho (carrinho_id);
CREATE INDEX idx_item_carrinho_produto ON item_carrinho (produto_id);