CREATE TABLE IF NOT EXISTS kunde (
    kundennummer VARCHAR2(255) PRIMARY KEY,
    name VARCHAR2(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS konto (
    kontonummer VARCHAR2(255) PRIMARY KEY,
    kreditrahmen NUMERIC NOT NULL,
    kunde VARCHAR2(255)
);

ALTER TABLE konto
    ADD CONSTRAINT IF NOT EXISTS fk_kunde
    FOREIGN KEY (kunde) REFERENCES kunde(kundennummer);

CREATE TABLE IF NOT EXISTS zahlung (
    id UUID PRIMARY KEY,
    datum TIMESTAMP WITH TIME ZONE DEFAULT now(),
    quelle VARCHAR2(255) NOT NULL,
    ziel VARCHAR2(255) NOT NULL,
    betrag NUMERIC NOT NULL
);

ALTER TABLE zahlung
    ADD CONSTRAINT IF NOT EXISTS fk_quelle
    FOREIGN KEY (quelle) REFERENCES konto(kontonummer);

ALTER TABLE zahlung
    ADD CONSTRAINT IF NOT EXISTS fk_ziel
    FOREIGN KEY (ziel) REFERENCES konto (kontonummer);
