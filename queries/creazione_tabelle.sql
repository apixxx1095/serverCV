CREATE TABLE CittadiniRegistrati (
nome VARCHAR NOT NULL,
cognome VARCHAR NOT NULL,
cf VARCHAR(16) UNIQUE NOT NULL,
mail VARCHAR NOT NULL,
userId VARCHAR PRIMARY KEY,
password VARCHAR NOT NULL,
idVaccinazione SMALLINT UNIQUE NOT NULL);

CREATE TABLE CentriVaccinali (
nomeCentro VARCHAR PRIMARY KEY,
qualificatore VARCHAR,
via VARCHAR NOT NULL,
numeroCivico INT NOT NULL,
comune VARCHAR NOT NULL,
provincia VARCHAR(2) NOT NULL,
cap VARCHAR(5) NOT NULL,
tipologia VARCHAR);

CREATE TABLE Vaccinati (
nomeCentro VARCHAR NOT NULL,
nome VARCHAR NOT NULL,
cognome VARCHAR NOT NULL,
cf VARCHAR(16) PRIMARY KEY,
data DATE,
vaccino VARCHAR,
idVaccinazione SMALLINT UNIQUE NOT NULL);


CREATE TABLE EventiAvversi (
nomeCentro VARCHAR REFERENCES CentriVaccinali,
evento VARCHAR,
severita NUMERIC,
note VARCHAR(256));