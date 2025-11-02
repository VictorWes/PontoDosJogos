CREATE TABLE produtos (

    -- Chave Primária e Geração Automática
    id BIGSERIAL PRIMARY KEY,

    -- Campos de Detalhe do Produto
    nome VARCHAR(255) NOT NULL,
    descricao TEXT NOT NULL, -- Usamos TEXT para descrições longas

    -- Preço: CRÍTICO! Usamos NUMERIC(10, 2) para precisão monetária
    preco NUMERIC(10, 2) NOT NULL,

    -- Estoque: O estoque deve ser no mínimo zero e ser obrigatório
    quantidade_em_estoque INTEGER NOT NULL CHECK (quantidade_em_estoque >= 0),

    -- Campos de Auditoria
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Índices (Melhora a performance de buscas e listagens)
CREATE INDEX idx_produto_nome ON produtos (nome);
CREATE INDEX idx_produto_preco ON produtos (preco);