
INSERT INTO restaurantes DEFAULT VALUES;

INSERT INTO gerentes (id) VALUES (1);
INSERT INTO caixas (id) VALUES (2);
INSERT INTO cozinhas (id) VALUES (3);

INSERT INTO garcons (id, restaurante_id) VALUES (4, 1);

INSERT INTO pagamentos DEFAULT VALUES; -- ID 1 (Será Dinheiro)
INSERT INTO pagamentos DEFAULT VALUES; -- ID 2 (Será Cartão)
INSERT INTO pagamentos DEFAULT VALUES; -- ID 3 (Será Cheque)

INSERT INTO pagamentos_dinheiro (id) VALUES (1);
INSERT INTO pagamentos_cartao (id, nro_transacao) VALUES (2, 987654321);
INSERT INTO pagamentos_cheque (id, numero) VALUES (3, 1001);

INSERT INTO clientes (nome, hora_chegada) VALUES
('João Silva', NOW() - INTERVAL '1 hour');

INSERT INTO categorias (nome, categoria_pai_id) VALUES
('Pratos Principais', NULL),
('Bebidas', NULL),
('Sobremesas', NULL);

INSERT INTO categorias (nome, categoria_pai_id) VALUES
('Carnes', 1),
('Sucos', 2);

INSERT INTO cardapios (gerente_id) VALUES (1);
INSERT INTO itens_cardapio (nome, ingredientes, preco, disponivel_na_cozinha, cardapio_id, categoria_id) VALUES
('Picanha Grelhada', 'Picanha, arroz, farofa', 79.90, true, 1, 4),
('Suco de Laranja', 'Laranja natural 500ml', 12.00, true, 1, 5),
('Pudim', 'Leite condensado, ovos, calda', 15.50, true, 1, 3);

INSERT INTO mesas (numero, disponivel, restaurante_id, garcom_id) VALUES
(10, false, 1, 4),
(11, true, 1, 4);

INSERT INTO contas (nome, pagamento_id, mesa_id) VALUES
('Mesa 10 - Jantar', 2, 1);

INSERT INTO conta_caixa_gerencia (conta_id, caixa_id) VALUES (1, 2);

INSERT INTO pedidos (numero, horario_pedido, conta_id, cliente_id, cozinha_id) VALUES
(1001, NOW() - INTERVAL '30 minutes', 1, 1, 3);

INSERT INTO itens_pedido (quantidade, pedido_id, item_cardapio_id) VALUES
(1.0, 1, 1),
(1.0, 1, 2);