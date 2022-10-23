! MODIFICA NOMI:

UPDATE menu
   SET nameDish = 'Faraona'
 WHERE nameDish = 'Faraona alla leccarda';
 
UPDATE menu
   SET nameDish = 'Tozzetti'
 WHERE nameDish = 'Tozzetti e vin santo';
 
  UPDATE menu
   SET nameDish = 'Polenta "bianca"'
 WHERE nameDish = 'Polenta al ragù bianco';

-- Table: menu
CREATE TABLE menu (nameDish VARCHAR PRIMARY KEY, price DOUBLE, course VARCHAR REFERENCES course (name), image STRING);

INSERT INTO menu (nameDish, price, course, image) VALUES ('Frittata al tartufo', 10.0, 'Antipasti', 'view/style/img/menu-dishes/frittata.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Suppl�', 7.0, 'Antipasti', 'view/style/img/menu-dishes/suppli.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Antipasto misto', 10.0, 'Antipasti', 'view/style/img/menu-dishes/antipasto-misto.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Bruschette', 7.0, 'Antipasti', 'view/style/img/menu-dishes/bruschette.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Scottadito d''agnello', 13.0, 'Secondi', 'view/style/img/menu-dishes/agnello.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Arrosto misto', 18.0, 'Secondi', 'view/style/img/menu-dishes/arrosto-misto.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Birra', 4.0, 'Bevande', 'view/style/img/menu-dishes/birra.png');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Bistecca', 14.0, 'Secondi', 'view/style/img/menu-dishes/bistecca.jpeg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Cheesecake', 7.0, 'Dolci', 'view/style/img/menu-dishes/cheesecake.jpeg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Cicoria', 7.0, 'Contorni', 'view/style/img/menu-dishes/cicoria.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Coratella', 8.0, 'Antipasti', 'view/style/img/menu-dishes/coratella.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Crema catalana', 7.0, 'Dolci', 'view/style/img/menu-dishes/crema-catalana.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Faraona', 18.0, 'Secondi', 'view/style/img/menu-dishes/faraona.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Fettuccine al rag�', 10.0, 'Primi', 'view/style/img/menu-dishes/fettuccine.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Gnocchi al castrato', 10.0, 'Primi', 'view/style/img/menu-dishes/gnocchi.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Insalata mista', 7.0, 'Contorni', 'view/style/img/menu-dishes/insalata.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Patate fritte', 6.0, 'Contorni', 'view/style/img/menu-dishes/patate-fritte.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Patate al forno', 6.0, 'Contorni', 'view/style/img/menu-dishes/patate.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Polenta "bianca"', 10.0, 'Primi', 'view/style/img/menu-dishes/polenta.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Risotto al tartufo', 13.0, 'Primi', 'view/style/img/menu-dishes/risotto.jpeg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Spezzatino di cinghiale', 14.0, 'Secondi', 'view/style/img/menu-dishes/spezzatino.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Tagliata di manzo', 15.0, 'Secondi', 'view/style/img/menu-dishes/tagliata.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Tiramis�', 7.0, 'Dolci', 'view/style/img/menu-dishes/tiramisu.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Torta del giorno', 7.0, 'Dolci', 'view/style/img/menu-dishes/torta.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Tozzetti ', 7.0, 'Dolci', 'view/style/img/menu-dishes/torta.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Verdure grigliate', 7.0, 'Contorni', 'view/style/img/menu-dishes/verdure-grigliate.jpg');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Vino rosso della casa', 8.0, 'Bevande', 'view/style/img/menu-dishes/zanchi.png');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Vino bianco della casa', 8.0, 'Bevande', 'view/style/img/menu-dishes/zanchi.png');
INSERT INTO menu (nameDish, price, course, image) VALUES ('Acqua', 1.0, 'Bevande', NULL);
INSERT INTO menu (nameDish, price, course, image) VALUES ('Coca cola', 3.0, 'Bevande', NULL);


COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
