CREATE TABLE carrinho (

    -- Chave Primária
    id BIGSERIAL PRIMARY KEY,

    -- Chave Estrangeira para Usuario (Tabela Pai: V1)
    usuario_id BIGINT NOT NULL,

    -- Campos de Estado
    status_carrinho VARCHAR(50) NOT NULL, -- Ex: 'ATIVO', 'CONCLUIDO', 'ABANDONADO'

    -- Campos de Auditoria
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Definição da Chave Estrangeira
    CONSTRAINT fk_carrinho_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario (id)
        ON DELETE CASCADE -- Se o usuário for deletado, seus carrinhos são removidos.
);

-- Índice: Rápida busca pelo carrinho ATIVO do usuário
CREATE INDEX idx_carrinho_usuario_status ON carrinho (usuario_id, status_carrinho);