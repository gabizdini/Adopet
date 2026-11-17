-- =====================================================================
-- Adopet - Script de criação das tabelas NOVAS (entregável da atividade)
-- Banco: crud_manager (MySQL / MariaDB)
--
-- Relacionamentos (todos 1:N):
--   users <- companies (Associações) <- abrigos <- animais
-- =====================================================================

USE crud_manager;

-- ---------------------------------------------------------------------
-- Login: senha dos usuários (hash SHA-256 em hexadecimal = 64 caracteres)
-- ---------------------------------------------------------------------
ALTER TABLE users
	ADD COLUMN password CHAR(64) NULL;

ALTER TABLE users
	ADD CONSTRAINT uk_users_email UNIQUE (email);

-- ---------------------------------------------------------------------
-- Abrigos: cada abrigo é mantido por uma Associação (tabela companies)
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

-- ---------------------------------------------------------------------
-- Animais: cada animal está em um abrigo
-- ---------------------------------------------------------------------
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
