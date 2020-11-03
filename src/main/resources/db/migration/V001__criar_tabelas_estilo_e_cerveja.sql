CREATE TABLE estilo (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE cerveja (
	codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
	sku VARCHAR(50) NOT NULL,
	nome VARCHAR(80) NOT NULL,
	descricao TEXT NOT NULL,
	valor DECIMAL(10, 2) NOT NULL,
	teor_alcoolico DECIMAL(10, 2) NOT NULL,
	comissao DECIMAL(10, 2) NOT NULL,
	sabor VARCHAR(50) NOT NULL,
	quantidade_estoque INT NOT NULL,
	origem VARCHAR(50) NOT NULL,
	codigo_estilo BIGINT NOT NULL,
	FOREIGN KEY (codigo_estilo) REFERENCES estilo(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO estilo (codigo, nome) VALUES (0, 'Amber lager');
INSERT INTO estilo (codigo, nome) VALUES (0, 'Dark Lager');
INSERT INTO estilo (codigo, nome) VALUES (0, 'Pale Lager');
INSERT INTO estilo (codigo, nome) VALUES (0, 'Pilsner');