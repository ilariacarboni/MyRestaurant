
! CAMBIARE TYPE begin_date, end_date in VARCHAR
! AGGIUNGERE COLONNA image

-- Table: employee
CREATE TABLE employee (codice_fiscale VARCHAR PRIMARY KEY, name VARCHAR, surname VARCHAR, role VARCHAR, begin_date VARCHAR, end_date VARCHAR, wage INTEGER, image STRING);


INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES ('STLGLI00S69L117S', 'Giulia', 'Astolfi', 'cameriere', '2020/12/01', '2022/12/01', 1200, NULL);
INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES ('CRBMLR00H48F844D', 'Maria Ilaria', 'Carboni', 'cameriere', '2020/12/01', '2022/12/01', 1200, '/view/style/img/employee-icons/imgila.png');
INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES ('BTTMSM62P30F257V', 'Massimo', 'Bottura', 'cuoco', '2020/12/01', '2022/12/01', 1800, '/view/style/img/employee-icons/bottura.png');
INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES ('LSSMRA58B21I608P', 'Mauro', 'Uliassi', 'cuoco', '2020/12/01', '2022/12/01', 1800, '/view/style/img/employee-icons/uliassi.png');
INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES (' GRRNTL00P60I921Q', 'Natalia', 'Guerrini', 'cameriere', '2020/12/01', '2022/12/01', 1200, NULL);
INSERT INTO employee (codice_fiscale, name, surname, role, begin_date, end_date, wage, image) VALUES ('MGNNNA69C47H501C', 'Maurizio', 'Serva', 'cuoco', '2020/12/01', '2022/12/01', 1800, '/view/style/img/employee-icons/maurizio.png');

