CREATE TABLE cardapios (
    id BIGSERIAL PRIMARY KEY,
    gerente_id BIGINT UNIQUE REFERENCES gerentes(id)
);

CREATE TABLE itens_cardapio (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255),
    ingredientes TEXT,
    preco REAL,
    disponivel_na_cozinha BOOLEAN,
    cardapio_id BIGINT REFERENCES cardapios(id),
    categoria_id BIGINT REFERENCES categorias(id)
);