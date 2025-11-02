CREATE TABLE endereco (

    -- Chave Primária e Geração Automática
    id BIGSERIAL PRIMARY KEY,

    -- Campos de Endereço
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(10) NOT NULL, -- Geralmente não é numérico, pode conter 'S/N'
    complemento VARCHAR(100),    -- Opcional
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,    -- Usar código de 2 letras (ex: SP, RJ)
    cep VARCHAR(8) NOT NULL,       -- CEP no formato limpo (8 dígitos)

    -- Chave Estrangeira (Relacionamento Many-to-One com Usuario)
    -- O 'usuario_id' é NOT NULL pois um endereço DEVE pertencer a um usuário.
    usuario_id BIGINT NOT NULL,

    -- Campos de Auditoria (Opcional, mas recomendado)
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    -- Definição da Chave Estrangeira
    CONSTRAINT fk_endereco_usuario
        FOREIGN KEY (usuario_id)
        REFERENCES usuario (id)
        ON DELETE CASCADE -- Se o usuário for deletado, seus endereços também são (Cascata)
);

-- Índices (Melhora a performance de buscas e JOINs)
CREATE INDEX idx_endereco_usuario_id ON endereco (usuario_id);
CREATE INDEX idx_endereco_cep ON endereco (cep);