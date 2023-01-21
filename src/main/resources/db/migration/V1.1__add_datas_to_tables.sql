INSERT INTO departments(name) VALUES ('Philosophy');
INSERT INTO departments(name) VALUES ('Mathematics');
INSERT INTO departments(name) VALUES ('Economy');
INSERT INTO departments(name) VALUES ('Biology');

INSERT INTO degree(name) VALUES ('assistant');
INSERT INTO degree(name) VALUES ('associate professor');
INSERT INTO degree(name) VALUES ('professor');

INSERT INTO lectors(firstname, lastname, payment, degree_id) VALUES ('Petro', 'Ivanov', 23000, 1);
INSERT INTO lectors(firstname, lastname, payment, degree_id) VALUES ('Semen', 'Petrov', 22000, 2);
INSERT INTO lectors(firstname, lastname, payment, degree_id, head_of_department) VALUES ('Ivan', 'Klemenko', 25000, 3, 3);
INSERT INTO lectors(firstname, lastname, payment, degree_id, head_of_department) VALUES ('Petro', 'Kolobok', 26000, 3 ,1);
INSERT INTO lectors(firstname, lastname, payment, degree_id, head_of_department) VALUES ('Ivan', 'Lys', 22000, 3 ,2);
INSERT INTO lectors(firstname, lastname, payment, degree_id, head_of_department) VALUES ('Evgen', 'Radionov', 23000, 3, 4);
INSERT INTO lectors(firstname, lastname, payment, degree_id) VALUES ('John', 'Johnsonuk',  21000, 2);
INSERT INTO lectors(firstname, lastname, payment, degree_id) VALUES ('Klemen', 'Zoev', 20000, 1);

INSERT INTO lectors_departments(lector_id, department_id) VALUES (1, 1);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (1, 2);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (1, 3);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (2, 1);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (2, 2);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (2, 4);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (3, 3);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (3, 4);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (4, 1);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (4, 3);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (5, 1);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (5, 2);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (5, 3);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (6, 4);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (7, 3);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (7, 1);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (8, 2);
INSERT INTO lectors_departments(lector_id, department_id) VALUES (8, 4);
