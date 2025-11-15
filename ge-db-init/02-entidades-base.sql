CREATE TABLE restaurantes (
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE pagamentos (
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE clientes (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    hora_chegada TIMESTAMP(6),
    hora_saida TIMESTAMP(6)
);

CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    categoria_pai_id BIGINT REFERENCES categorias(id)
);