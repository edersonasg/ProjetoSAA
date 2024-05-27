-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS seu_banco_de_dados;
USE seu_banco_de_dados;

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(8) UNIQUE NOT NULL,
    bitola DECIMAL(5,2) NOT NULL,
    comprimento DECIMAL(8,2) NOT NULL,
    cor VARCHAR(20) NOT NULL,
    alocacao VARCHAR(20) NOT NULL
);

-- Tabela de Cassetes
CREATE TABLE IF NOT EXISTS cassetes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cassete VARCHAR(20) UNIQUE NOT NULL,
    nivel VARCHAR(10) NOT NULL, -- Aumentando o tamanho para 10 caracteres
    estado ENUM('Vazio', 'Ocupado') NOT NULL DEFAULT 'Vazio'
);

-- Inserir cassetes de acordo com os níveis especificados
INSERT INTO cassetes (cassete, nivel) VALUES
-- Nível A (cassete do 1 ao 25)
('Cassete 1', 'Nível A'),
('Cassete 2', 'Nível A'),
('Cassete 3', 'Nível A'),
('Cassete 4', 'Nível A'),
('Cassete 5', 'Nível A'),
('Cassete 6', 'Nível A'),
('Cassete 7', 'Nível A'),
('Cassete 8', 'Nível A'),
('Cassete 9', 'Nível A'),
('Cassete 10', 'Nível A'),
('Cassete 11', 'Nível A'),
('Cassete 12', 'Nível A'),
('Cassete 13', 'Nível A'),
('Cassete 14', 'Nível A'),
('Cassete 15', 'Nível A'),
('Cassete 16', 'Nível A'),
('Cassete 17', 'Nível A'),
('Cassete 18', 'Nível A'),
('Cassete 19', 'Nível A'),
('Cassete 20', 'Nível A'),
('Cassete 21', 'Nível A'),
('Cassete 22', 'Nível A'),
('Cassete 23', 'Nível A'),
('Cassete 24', 'Nível A'),
('Cassete 25', 'Nível A'),
-- Nível B (cassete do 26 ao 50)
-- Adicionar os cassetes do nível B aqui
('Cassete 26', 'Nível B'),
('Cassete 27', 'Nível B'),
('Cassete 28', 'Nível B'),
('Cassete 29', 'Nível B'),
('Cassete 30', 'Nível B'),
('Cassete 31', 'Nível B'),
('Cassete 32', 'Nível B'),
('Cassete 33', 'Nível B'),
('Cassete 34', 'Nível B'),
('Cassete 35', 'Nível B'),
('Cassete 36', 'Nível B'),
('Cassete 37', 'Nível B'),
('Cassete 38', 'Nível B'),
('Cassete 39', 'Nível B'),
('Cassete 40', 'Nível B'),
('Cassete 41', 'Nível B'),
('Cassete 42', 'Nível B'),
('Cassete 43', 'Nível B'),
('Cassete 44', 'Nível B'),
('Cassete 45', 'Nível B'),
('Cassete 46', 'Nível B'),
('Cassete 47', 'Nível B'),
('Cassete 48', 'Nível B'),
('Cassete 49', 'Nível B'),
('Cassete 50', 'Nível B'),
-- Nível C (cassete do 51 ao 75)
-- Adicionar os cassetes do nível C aqui
('Cassete 51', 'Nível C'),
('Cassete 52', 'Nível C'),
('Cassete 53', 'Nível C'),
('Cassete 54', 'Nível C'),
('Cassete 55', 'Nível C'),
('Cassete 56', 'Nível C'),
('Cassete 57', 'Nível C'),
('Cassete 58', 'Nível C'),
('Cassete 59', 'Nível C'),
('Cassete 60', 'Nível C'),
('Cassete 61', 'Nível C'),
('Cassete 62', 'Nível C'),
('Cassete 63', 'Nível C'),
('Cassete 64', 'Nível C'),
('Cassete 65', 'Nível C'),
('Cassete 66', 'Nível C'),
('Cassete 67', 'Nível C'),
('Cassete 68', 'Nível C'),
('Cassete 69', 'Nível C'),
('Cassete 70', 'Nível C'),
('Cassete 71', 'Nível C'),
('Cassete 72', 'Nível C'),
('Cassete 73', 'Nível C'),
('Cassete 74', 'Nível C'),
('Cassete 75', 'Nível C'),
-- Nível D (cassete do 76 ao 100)
-- Adicionar os cassetes do nível D aqui
('Cassete 76', 'Nível D'),
('Cassete 77', 'Nível D'),
('Cassete 78', 'Nível D'),
('Cassete 79', 'Nível D'),
('Cassete 80', 'Nível D'),
('Cassete 81', 'Nível D'),
('Cassete 82', 'Nível D'),
('Cassete 83', 'Nível D'),
('Cassete 84', 'Nível D'),
('Cassete 85', 'Nível D'),
('Cassete 86', 'Nível D'),
('Cassete 87', 'Nível D'),
('Cassete 88', 'Nível D'),
('Cassete 89', 'Nível D'),
('Cassete 90', 'Nível D'),
('Cassete 91', 'Nível D'),
('Cassete 92', 'Nível D'),
('Cassete 93', 'Nível D'),
('Cassete 94', 'Nível D'),
('Cassete 95', 'Nível D'),
('Cassete 96', 'Nível D'),
('Cassete 97', 'Nível D'),
('Cassete 98', 'Nível D'),
('Cassete 99', 'Nível D'),
('Cassete 100', 'Nível D');
-- Verificar a criação das tabelas
SHOW TABLES;
-- Verificar a estrutura da tabela de produtos
DESCRIBE produtos;
-- Verificar a estrutura da tabela de cassetes
DESCRIBE cassetes;