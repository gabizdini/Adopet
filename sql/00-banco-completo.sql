-- =====================================================================
-- Adopet - Script COMPLETO do banco crud_manager
-- Recria TODAS as tabelas (users, posts, companies, abrigos, animais)
-- e insere dados de exemplo (suficientes para testar a paginação).
--
-- ATENÇÃO: apaga as tabelas existentes e os dados delas.
--
-- Usuários de teste (senha em SHA-256):
--   admin@adopet.com  / admin123
--   demais usuários   / senha123
-- =====================================================================

CREATE DATABASE IF NOT EXISTS crud_manager
	DEFAULT CHARACTER SET utf8mb4;

USE crud_manager;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS animais;
DROP TABLE IF EXISTS abrigos;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS companies;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------------------
-- Tabelas do projeto base (CRUD Manager)
-- ---------------------------------------------------------------------
CREATE TABLE users (
	id       INT          NOT NULL AUTO_INCREMENT,
	nome     VARCHAR(100) NOT NULL,
	sexo     CHAR(1)      NOT NULL,
	email    VARCHAR(100) NOT NULL,
	password CHAR(64)     NULL,
	PRIMARY KEY (id),
	CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE posts (
	id        INT  NOT NULL AUTO_INCREMENT,
	content   TEXT NOT NULL,
	post_date DATE NOT NULL,
	user_id   INT  NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_posts_users
		FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- companies = Associações / ONGs mantenedoras
CREATE TABLE companies (
	id      INT          NOT NULL AUTO_INCREMENT,
	name    VARCHAR(100) NOT NULL,
	role    VARCHAR(100) NOT NULL,
	start   DATE         NOT NULL,
	end     DATE         NULL,
	user_id INT          NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_companies_users
		FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------------------------------------------------------------------
-- Tabelas novas (mesmo conteúdo de 01-novas-tabelas.sql)
-- ---------------------------------------------------------------------
CREATE TABLE abrigos (
	id                 INT          NOT NULL AUTO_INCREMENT,
	nome               VARCHAR(100) NOT NULL,
	telefone           VARCHAR(20)  NOT NULL,
	data_fundacao      DATE         NULL,
	capacidade         INT          NOT NULL,
	aceita_voluntarios TINYINT(1)   NOT NULL DEFAULT 0,
	descricao          TEXT         NULL,
	company_id         INT          NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_abrigos_companies
		FOREIGN KEY (company_id) REFERENCES companies (id)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

CREATE TABLE animais (
	id           INT          NOT NULL AUTO_INCREMENT,
	nome         VARCHAR(100) NOT NULL,
	especie      VARCHAR(20)  NOT NULL,
	porte        VARCHAR(20)  NOT NULL,
	data_resgate DATE         NOT NULL,
	peso         DECIMAL(5,2) NOT NULL,
	castrado     TINYINT(1)   NOT NULL DEFAULT 0,
	status       VARCHAR(20)  NOT NULL,
	abrigo_id    INT          NOT NULL,
	PRIMARY KEY (id),
	CONSTRAINT fk_animais_abrigos
		FOREIGN KEY (abrigo_id) REFERENCES abrigos (id)
		ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ---------------------------------------------------------------------
-- Dados de exemplo
-- ---------------------------------------------------------------------
INSERT INTO users (nome, sexo, email, password) VALUES
	('Administrador',  'F', 'admin@adopet.com',  '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9'),
	('Maria Souza',    'F', 'maria@adopet.com',  '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
	('João Pereira',   'M', 'joao@adopet.com',   '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
	('Ana Lima',       'F', 'ana@adopet.com',    '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
	('Carlos Mendes',  'M', 'carlos@adopet.com', '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
	('Beatriz Rocha',  'F', 'bia@adopet.com',    '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251'),
	('Pedro Alves',    'M', 'pedro@adopet.com',  '55a5e9e78207b4df8699d60886fa070079463547b095d1a05bc719bb4e6cd251');

INSERT INTO posts (content, post_date, user_id) VALUES
	('Feira de adoção neste sábado na praça central!',       '2026-10-01', 1),
	('Precisamos de doações de ração para filhotes.',         '2026-10-05', 2),
	('Campanha de castração gratuita em novembro.',           '2026-10-10', 3),
	('Voluntários: mutirão de limpeza no abrigo domingo.',    '2026-10-15', 4),
	('Thor foi adotado! Obrigado a todos que ajudaram.',      '2026-10-20', 5),
	('Novo horário de visitas: terça a sábado, 9h às 17h.',   '2026-10-25', 6);

INSERT INTO companies (name, role, start, end, user_id) VALUES
	('Patas Felizes',         'Presidente',  '2018-03-01', NULL, 2),
	('Amigos dos Bichos',     'Presidente',  '2015-07-15', NULL, 3),
	('SOS Animais Machado',   'Coordenador', '2020-01-10', NULL, 5),
	('Lar Temporário Pet',    'Tesoureira',  '2019-05-20', NULL, 4),
	('Focinho Gelado',        'Presidente',  '2021-09-01', NULL, 6),
	('Proteção Animal Sul',   'Secretário',  '2017-11-11', NULL, 7);

INSERT INTO abrigos (nome, telefone, data_fundacao, capacidade, aceita_voluntarios, descricao, company_id) VALUES
	('Abrigo Esperança',     '(35) 99999-1001', '2018-04-10', 40, 1, 'Abrigo para cães de todos os portes.',        1),
	('Cantinho dos Gatos',   '(35) 99999-1002', '2019-02-01', 25, 1, 'Especializado em felinos.',                   1),
	('Recanto Amigo',        '(35) 99999-1003', '2015-08-20', 60, 0, 'Maior abrigo da região.',                     2),
	('Casa do Vira-Lata',    '(35) 99999-1004', '2020-03-15', 30, 1, 'Foco em animais resgatados das ruas.',        3),
	('Abrigo Sol Nascente',  '(35) 99999-1005', NULL,         15, 1, 'Lar temporário para filhotes.',               4),
	('Santuário Focinho',    '(35) 99999-1006', '2021-10-01', 20, 0, 'Recebe animais idosos e com deficiência.',    5);

INSERT INTO animais (nome, especie, porte, data_resgate, peso, castrado, status, abrigo_id) VALUES
	('Thor',    'Cachorro', 'Grande',  '2026-01-10', 28.50, 1, 'Adotado',     1),
	('Mel',     'Cachorro', 'Médio',   '2026-02-14', 12.30, 1, 'Disponível',  1),
	('Luna',    'Gato',     'Pequeno', '2026-03-05',  3.80, 1, 'Disponível',  2),
	('Simba',   'Gato',     'Pequeno', '2026-03-22',  4.10, 0, 'Em processo', 2),
	('Bob',     'Cachorro', 'Pequeno', '2026-04-01',  7.20, 1, 'Disponível',  3),
	('Nina',    'Cachorro', 'Médio',   '2026-04-18', 15.00, 0, 'Disponível',  3),
	('Pipoca',  'Outro',    'Pequeno', '2026-05-02',  0.90, 0, 'Disponível',  4),
	('Rex',     'Cachorro', 'Grande',  '2026-05-30', 32.00, 1, 'Em processo', 4),
	('Mia',     'Gato',     'Pequeno', '2026-06-12',  3.20, 1, 'Adotado',     5),
	('Fred',    'Cachorro', 'Médio',   '2026-07-07', 11.80, 1, 'Disponível',  6),
	('Amora',   'Gato',     'Pequeno', '2026-08-19',  2.70, 0, 'Disponível',  5),
	('Paçoca',  'Cachorro', 'Pequeno', '2026-09-03',  6.40, 1, 'Disponível',  6);
