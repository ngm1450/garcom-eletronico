CREATE TABLE mesas (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER,
    disponivel BOOLEAN,
    restaurante_id BIGINT REFERENCES restaurantes(id),
    garcom_id BIGINT REFERENCES garcons(id)
);

CREATE TABLE contas (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    pagamento_id BIGINT REFERENCES pagamentos(id),
    mesa_id BIGINT REFERENCES mesas(id)
);

CREATE TABLE conta_caixa_gerencia (
    conta_id BIGINT REFERENCES contas(id),
    caixa_id BIGINT REFERENCES caixas(id),
    PRIMARY KEY (conta_id, caixa_id)
);

CREATE TABLE pedidos (
    id BIGSERIAL PRIMARY KEY,
    numero INTEGER,
    horario_pedido TIMESTAMP(6),
    horario_entrega TIMESTAMP(6),
    conta_id BIGINT REFERENCES contas(id),
    cliente_id BIGINT REFERENCES clientes(id),
    cozinha_id BIGINT REFERENCES cozinhas(id)
);

CREATE TABLE itens_pedido (
    id BIGSERIAL PRIMARY KEY,
    quantidade REAL,
    pedido_id BIGINT REFERENCES pedidos(id),
    item_cardapio_id BIGINT REFERENCES itens_cardapio(id)
);