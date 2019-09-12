DELETE
FROM zahlung;
DELETE
FROM konto;
DELETE
FROM kunde;

INSERT INTO kunde(kundennummer, name)
VALUES  ('000', 'ZINS'),
        ('1', 'Hannes'),
        ('2', 'Werner');

INSERT INTO konto(kontonummer, kreditrahmen, kunde)
VALUES ('000', '1000000', '000'),
       ('1', '200.00', '1'),
       ('2', '0', '2');


