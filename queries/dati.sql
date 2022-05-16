INSERT INTO CittadiniRegistrati (nome, cognome, cf, mail, userId, password, idVaccinazione)
VALUES ('Carlo', 'Mazzini', 'MZZCRL90A01F205C', 'cmazzini@hotmail.it', '00103885', 'madonina1', 9965),
('Lucia', 'Pifferai', 'PFFLCU85A41A062U', 'pifferola85@gmail.com', '12094677', 'pifferomagico9', 9928),
('Mario', 'Marino', 'MRNMRA71S08I469M', 'mariottide777@gmail.com', '00015432', 'mariomario64', 9724),
('Mattia', 'Galati', 'GLTMTT86M16D653S', 'galassiamatta@gmail.com', '23023989', 'gigachad86', 7482),
('Carola', 'De Rocchi', 'DRCCRL00A63C751L', 'christmascarol2000@outlook.com', '28547834', 'papayareggae69', 17456),
('Giuseppina', 'Lanzini', 'LNZGPP49A41D869G', 'peppalanzi@libero.it', '00564573', 'peppalanzi1', 3267),
('Gianfranco', 'Andinolfi', 'NDNGFR94D27G795T', 'blaziken68@gmail.com', '21367342', 'magnatarallo68', 8789),
('Mattia', 'Renzio', 'RNZMTT75A11D612H', 'shishkebab@libero.it', '09547458', 'pompea18', 2569),
('Ernesto', 'Valgrande', 'VLGRST69E31L103I', 'earnest@gmail.com', '14352498', 'equelladellalola666', 1897),
('Daniela', 'Caraffa', 'CRFDNL96M44B519H', 'duraruza@outlook.com', '23456789', 'vattelapesca53', 6598);

INSERT INTO CentriVaccinali (nomeCentro, qualificatore, via, numeroCivico, comune, provincia, cap, tipologia)
VALUES ('Centro Prelievi Poliambulatorio di Arcisate', 'via', 'campi maggiori', 23, 'Arcisate', 'VA', '21051', 'hub'),
('Azienda Ospedaliera A. Cardarelli', 'via', 'Antonio Cardarelli', 9, 'Napoli', 'NA', '80131', 'ospedaliero'),
('Policlinico Universitario Agostino Gemelli', 'via', 'della Pineta Sacchetti', 217, 'Roma', 'RM', '00168', 'ospedaliero'),
('San Raffaele', 'via', 'Olgettina', 60, 'Milano', 'MI', '20132', 'ospedaliero'),
('Azienda ospedaliero-universitaria Meyer', 'viale', 'Gaetano Pieraccini', 24, 'Firenze', 'FI', '50139', 'ospedaliero');

INSERT INTO Vaccinati (nomeCentro, nome, cognome, cf, data, vaccino, idVaccinazione )
VALUES ('San Raffaele', 'Carlo', 'Mazzini', 'MZZCRL90A01F205C', '01-01-2021', 'Pfizer', 9965),
('Policlinico Universitario Agostino Gemelli', 'Lucia', 'Pifferai', 'PFFLCU85A41A062U', '15-12-2020', 'J&J', 9928),
('Azienda Ospedaliera A. Cardarelli', 'Mario', 'Marino', 'MRNMRA71S08I469M', '10-11-2020', 'AstraZeneca', 9724),
('San Raffaele', 'Mattia', 'Galati', 'GLTMTT86M16D653S', '14-08-2020', 'Pfizer', 7482),
('Centro Prelievi Poliambulatorio di Arcisate', 'Carola', 'De Rocchi', 'DRCCRL00A63C751L', '15-03-2022', 'Moderna', 17456),
('Centro Prelievi Poliambulatorio di Arcisate', 'Giuseppina', 'Lanzini', 'LNZGPP49A41D869G', '10-03-2020', 'AstraZeneca', 3267),
('Azienda Ospedaliera A. Cardarelli', 'Gianfranco', 'Andinolfi', 'NDNGFR94D27G795T', '09-09-2020', 'J&J', 8789),
('Azienda ospedaliero-universitaria Meyer', 'Mattia', 'Renzio', 'RNZMTT75A11D612H', '03-06-2020', 'Pfizer', 2569),
('Policlinico Universitario Agostino Gemelli', 'Ernesto', 'Valgrande', 'VLGRST69E31L103I', '01-02-2020', 'Pfizer', 1897),
('Azienda ospedaliero-universitaria Meyer', 'Daniela', 'Caraffa', 'CRFDNL96M44B519H', '17-07-2020', 'Moderna', 6598);

INSERT INTO EventiAvversi (nomeCentro, evento, severita, note)
VALUES ('Azienda Ospedaliera A. Cardarelli', 'Mal di testa', 4, ''),
('Azienda Ospedaliera A. Cardarelli', 'Linfoadenopatia', 5, ''),
('Centro Prelievi Poliambulatorio di Arcisate', 'Tachicardia', 5, ''),
('Centro Prelievi Poliambulatorio di Arcisate', 'Tachicardia', 2, ''),
('San Raffaele', 'Mal di testa', 3, ''),
('Azienda ospedaliero-universitaria Meyer', 'Linfoadenopatia', 1, '');
