CREATE TABLE usuario (

    -- Chave Primária e Geração Automática (BIGSERIAL = BIGINT + Sequence/IDENTITY)
    id BIGSERIAL PRIMARY KEY,

    -- Campos de Cadastro
    -- VARCHAR é melhor que TEXT para campos com limite de tamanho conhecido
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,

    -- Senha: Armazenada como HASH (BCrypt), precisa ser longa o suficiente (255 é um bom padrão)
    senha_hash VARCHAR(255) NOT NULL,

    -- Campos de Auditoria (Essenciais para rastreamento)
    -- TIMESTAMP WITHOUT TIME ZONE é o tipo padrão do Postgres para datas/horas sem fuso
    data_criacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- INDICES E CONSTRAINTS

-- 1. Constraint de Unicidade: Crucial para o login
-- Garante que o email seja único em todo o banco de dados.
CREATE UNIQUE INDEX idx_usuario_email ON usuario (email);

-- 2. Índice para Pesquisas (Melhora performance em consultas por nome, se necessário)
CREATE INDEX idx_usuario_nome ON usuario (nome)