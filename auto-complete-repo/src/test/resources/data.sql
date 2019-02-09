/*
CREATE TABLE mst_city (
    id INT(11) NOT NULL,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT pk_mst_city PRIMARY KEY('id'),
    CONSTRAINT uk_mst_city_name UNIQUE('name')
);

CREATE TABLE mst_state (
    id INT(11) NOT NULL,
    name VARCHAR(30) NOT NULL,
    CONSTRAINT pk_mst_state PRIMARY KEY('id'),
    CONSTRAINT uk_mst_state_name UNIQUE('name')
);
*/

INSERT INTO mst_city (id, name) VALUES (1, 'Mumbai');
INSERT INTO mst_city (id, name) VALUES (2, 'Hyderabad');
INSERT INTO mst_city (id, name) VALUES (3, 'New Delhi');
INSERT INTO mst_city (id, name) VALUES (4, 'Banglore');
INSERT INTO mst_city (id, name) VALUES (5, 'Pune');
INSERT INTO mst_city (id, name) VALUES (6, 'Noida');
INSERT INTO mst_city (id, name) VALUES (7, 'Chennai');
INSERT INTO mst_city (id, name) VALUES (8, 'Kolkata');

INSERT INTO mst_state (id, name) VALUES (1, 'Maharashtra');
INSERT INTO mst_state (id, name) VALUES (2, 'Telangana');
INSERT INTO mst_state (id, name) VALUES (3, 'Tamilnadu');
INSERT INTO mst_state (id, name) VALUES (4, 'Karnataka');
INSERT INTO mst_state (id, name) VALUES (5, 'Andhrapradesh');
INSERT INTO mst_state (id, name) VALUES (6, 'Gujarat');
INSERT INTO mst_state (id, name) VALUES (7, 'Madhyapradesh');
INSERT INTO mst_state (id, name) VALUES (8, 'Aasaam');
