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
       ('12340001', '200.00', '1'),
       ('12340002', '0', '2');


