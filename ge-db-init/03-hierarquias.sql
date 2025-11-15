CREATE TABLE gerentes (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id)
);

CREATE TABLE caixas (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id)
);

CREATE TABLE cozinhas (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id)
);

CREATE TABLE garcons (
    id BIGINT PRIMARY KEY REFERENCES usuarios(id),
    restaurante_id BIGINT REFERENCES restaurantes(id)
);

CREATE TABLE pagamentos_dinheiro (
    id BIGINT PRIMARY KEY REFERENCES pagamentos(id)
);

CREATE TABLE pagamentos_cartao (
    id BIGINT PRIMARY KEY REFERENCES pagamentos(id),
    nro_transacao INTEGER
);

CREATE TABLE pagamentos_cheque (
    id BIGINT PRIMARY KEY REFERENCES pagamentos(id),
    numero INTEGER
);