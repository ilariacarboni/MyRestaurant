--
-- File generated with SQLiteStudio v3.3.3 on ven ott 28 22:06:00 2022
--
-- Text encoding used: System
--
! AGGIUNTA COLONNA state VARCHAR

! ISTANZE AGGIORNATE

UPDATE utility
   SET  type = 'elettricità'
 WHERE numberId = '3' ;

UPDATE utility
   SET  type = 'acqua'
 WHERE numberId = '4' ;



CREATE TABLE utility (numberId INTEGER PRIMARY KEY, total DOUBLE, type VARCHAR, date DATE, state VARCHAR);

INSERT INTO utility (numberId, total, type, date, state) VALUES (3, 402.0, 'elettricità', '2021-09-12 00:00:00', NULL);
INSERT INTO utility (numberId, total, type, date, state) VALUES (4, 397.0, 'acqua', '2021-09-13 00:00:00', NULL);
