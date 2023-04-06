--Inserindo Pessoas
INSERT INTO person (name, birthday) VALUES ('Jonathan Neves', '1998-02-15');
INSERT INTO person (name, birthday) VALUES ('Maria Rocha', '1978-05-11');
INSERT INTO person (name, birthday) VALUES ('João da Silva', '2002-11-02');
INSERT INTO person (name, birthday) VALUES ('Fernanda Flores', '1995-08-08');

--Inserindo Endereços
INSERT INTO address (public_area, cep, number, city, main, person_id) VALUES ('Hospital', '88555012', '123', 'Tubarão', true, 1);
INSERT INTO address (public_area, cep, number, city, main, person_id) VALUES ('Prédio', '89432333', '456123', 'Criciúma', false, 1);
INSERT INTO address (public_area, cep, number, city, main, person_id) VALUES ('Casa Azul', '88000111', '999000', 'Laguna', true, 2);
