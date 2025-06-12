-- =====================
-- INÍCIO DA TRANSAÇÃO
-- =====================
BEGIN;

-- =====================
-- CATEGORIAS (4)
-- =====================
INSERT INTO categoria (nome) 
SELECT 'ROUPAS' 
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'ROUPAS');

INSERT INTO categoria (nome) 
SELECT 'BRINQUEDOS' 
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'BRINQUEDOS');

INSERT INTO categoria (nome) 
SELECT 'TECNOLOGIA' 
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'TECNOLOGIA');

INSERT INTO categoria (nome) 
SELECT 'MOVEIS' 
WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'MOVEIS');

-- =====================
-- PRODUTOS (20)
-- =====================
-- Roupas
INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Baseball Cap', 'bone de baseball', 
    (SELECT id FROM categoria WHERE nome = 'ROUPAS'), 
    20.50, 10, 'China S.A', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Baseball Cap');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Camisa Verde', 'camisa de algodao', 
    (SELECT id FROM categoria WHERE nome = 'ROUPAS'), 
    35.00, 15, 'Brasil Têxtil', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Camisa Verde');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Jaqueta', 'jaqueta preta', 
    (SELECT id FROM categoria WHERE nome = 'ROUPAS'), 
    80.00, 7, 'FashionWear Ltda', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Jaqueta');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Tennis de Corrida', 'tenis esportivo', 
    (SELECT id FROM categoria WHERE nome = 'ROUPAS'), 
    120.00, 20, 'Adisport', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Tennis de Corrida');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Oculos Escuro', 'Oculos de sol escuro', 
    (SELECT id FROM categoria WHERE nome = 'ROUPAS'), 
    25.00, 13, 'Sport S.A', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Oculos Escuro');

-- Brinquedos
INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Ursinho de Pelucia', 'ursinho macio', 
    (SELECT id FROM categoria WHERE nome = 'BRINQUEDOS'), 
    50.00, 18, 'ToyCo', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Ursinho de Pelucia');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Arma de Brinquedo', 'arma de brinquedo laranja', 
    (SELECT id FROM categoria WHERE nome = 'BRINQUEDOS'), 
    30.00, 12, 'Nerfun', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Arma de Brinquedo');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Lego', 'pecas de montar', 
    (SELECT id FROM categoria WHERE nome = 'BRINQUEDOS'), 
    60.00, 25, 'Lego Group', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Lego');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Boomerang', 'boomerang infantil', 
    (SELECT id FROM categoria WHERE nome = 'BRINQUEDOS'), 
    15.00, 9, 'BoomPlay', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Boomerang');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Bola de Futebol', 'bola de futebol', 
    (SELECT id FROM categoria WHERE nome = 'BRINQUEDOS'), 
    45.00, 22, 'TopSports', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Bola de Futebol');

-- Tecnologia
INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Smartphone', 'telefone inteligente', 
    (SELECT id FROM categoria WHERE nome = 'TECNOLOGIA'), 
    1500.00, 5, 'TechPhone', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Smartphone');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Smartwatch', 'relogio digital', 
    (SELECT id FROM categoria WHERE nome = 'TECNOLOGIA'), 
    999.99, 8, 'SmartCo', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Smartwatch');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Laptop', 'computador portatil', 
    (SELECT id FROM categoria WHERE nome = 'TECNOLOGIA'), 
    3500.00, 3, 'NotePro', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Laptop');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Camera', 'camera fotografica', 
    (SELECT id FROM categoria WHERE nome = 'TECNOLOGIA'), 
    2000.00, 4, 'CanonX', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Camera');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Headphone', 'fone bluetooth', 
    (SELECT id FROM categoria WHERE nome = 'TECNOLOGIA'), 
    180.00, 10, 'SoundX', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Headphone');

-- Móveis
INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Sofa', 'sofa confortavel', 
    (SELECT id FROM categoria WHERE nome = 'MOVEIS'), 
    800.00, 2, 'CasaBela', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Sofa');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Poltrona', 'poltrona reclinavel', 
    (SELECT id FROM categoria WHERE nome = 'MOVEIS'), 
    450.00, 3, 'Móveis Premium', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Poltrona');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Cafeteira', 'cafeteira moderna', 
    (SELECT id FROM categoria WHERE nome = 'MOVEIS'), 
    250.00, 6, 'Café Top', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Cafeteira');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Liquidificador', 'liquidificador potente', 
    (SELECT id FROM categoria WHERE nome = 'MOVEIS'), 
    200.00, 9, 'EletroMix', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Liquidificador');

INSERT INTO produto (nome, descricao, id_categoria, preco, estoque, fabricante, ativo, data_cadastro, data_atualizacao)
SELECT 'Faca', 'faca inox', 
    (SELECT id FROM categoria WHERE nome = 'MOVEIS'), 
    70.00, 11, 'Tramix', true, CURRENT_DATE, CURRENT_DATE
WHERE NOT EXISTS (SELECT 1 FROM produto WHERE nome = 'Faca');

-- =====================
-- FOTOS (20 PRODUTOS x 20 fotos = 400 linhas)
-- =====================
-- Roupas
INSERT INTO foto (nome, id_produto)
SELECT 'baseball-cap.png', p.id
FROM produto p
WHERE p.nome = 'Baseball Cap'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'baseball-cap.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'green-shirt.png', p.id
FROM produto p
WHERE p.nome = 'Camisa Verde'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'green-shirt.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'jacket.png', p.id
FROM produto p
WHERE p.nome = 'Jaqueta'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'jacket.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'shoes.png', p.id
FROM produto p
WHERE p.nome = 'Tennis de Corrida'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'shoes.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'sunglasses.png', p.id
FROM produto p
WHERE p.nome = 'Oculos Escuro'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'sunglasses.png' 
    AND f.id_produto = p.id
);

-- Brinquedos
INSERT INTO foto (nome, id_produto)
SELECT 'teddy-bear.png', p.id
FROM produto p
WHERE p.nome = 'Ursinho de Pelucia'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'teddy-bear.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'toy-gun.png', p.id
FROM produto p
WHERE p.nome = 'Arma de Brinquedo'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'toy-gun.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'lego.png', p.id
FROM produto p
WHERE p.nome = 'Lego'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'lego.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'boomerang.png', p.id
FROM produto p
WHERE p.nome = 'Boomerang'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'boomerang.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'ball.png', p.id
FROM produto p
WHERE p.nome = 'Bola de Futebol'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'ball.png' 
    AND f.id_produto = p.id
);

-- Tecnologia
INSERT INTO foto (nome, id_produto)
SELECT 'smart-phone.png', p.id
FROM produto p
WHERE p.nome = 'Smartphone'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'smart-phone.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'smart-watch.png', p.id
FROM produto p
WHERE p.nome = 'Smartwatch'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'smart-watch.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'laptop.png', p.id
FROM produto p
WHERE p.nome = 'Laptop'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'laptop.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'camera.png', p.id
FROM produto p
WHERE p.nome = 'Camera'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'camera.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'headphone.png', p.id
FROM produto p
WHERE p.nome = 'Headphone'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'headphone.png' 
    AND f.id_produto = p.id
);

-- Casa e Cozinha
INSERT INTO foto (nome, id_produto)
SELECT 'sofa.png', p.id
FROM produto p
WHERE p.nome = 'Sofa'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'sofa.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'armchair.png', p.id
FROM produto p
WHERE p.nome = 'Poltrona'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'armchair.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'coffee-maker.png', p.id
FROM produto p
WHERE p.nome = 'Cafeteira'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'coffee-maker.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'blender.png', p.id
FROM produto p
WHERE p.nome = 'Liquidificador'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'blender.png' 
    AND f.id_produto = p.id
);

INSERT INTO foto (nome, id_produto)
SELECT 'knife.png', p.id
FROM produto p
WHERE p.nome = 'Faca'
AND NOT EXISTS (
    SELECT 1 FROM foto f 
    WHERE f.nome = 'knife.png' 
    AND f.id_produto = p.id
);

-- =====================
-- FIM DA TRANSAÇÃO
-- =====================
COMMIT;



