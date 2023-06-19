connect c##develop/oracle

--/
BEGIN
-- Dodanie typow srodkow

  INSERT INTO typ_srodkow_encja_slownikowa (nazwa_funduszu)
  VALUES ('Wypoczynek wakacyjny');

  INSERT INTO typ_srodkow_encja_slownikowa (nazwa_funduszu)
  VALUES ('Zajecia sportowo-rekreacyjne');

  INSERT INTO typ_srodkow_encja_slownikowa (nazwa_funduszu)
  VALUES ('Imprezy kulturalne');

  INSERT INTO typ_srodkow_encja_slownikowa (nazwa_funduszu)
  VALUES ('Zapomogi');

  INSERT INTO typ_srodkow_encja_slownikowa (nazwa_funduszu)
  VALUES ('Pomoc mieszkaniowa');


-- Dodanie Wartosci funduszu
INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Wypoczynek wakacyjny', 2022, 10000, 9000, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Zajecia sportowo-rekreacyjne', 2022, 1000, 1000, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Imprezy kulturalne', 2022, 3000, 200, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Zapomogi', 2022, 20000, 20000, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Pomoc mieszkaniowa', 2022, 10000, 9000, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Wypoczynek wakacyjny', 2023, 10000, 0, 1000);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Zajecia sportowo-rekreacyjne', 2023, 0, 1000, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Imprezy kulturalne', 2023, 3000, 2950, 2800);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Zapomogi', 2023, 20000, 0, NULL);

INSERT INTO opis_funduszu (typ_srodkow_encja_slownikowa_nazwa_funduszu, rok, kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku)
  VALUES ('Pomoc mieszkaniowa', 2023, 1000, 0, 1000);
COMMIT;
END;
/
--/
BEGIN
-- Dodanie formularzy

  DodajFormularz(
  'Formularz o wypoczynek wakacyjny',
  'Wypoczynek wakacyjny',
  'kwota,miejsce wakacji,zakres czasowy wypoczynku,ilosc osob',
  'float,string,string,int',
  '100,50,20,10');

    DodajFormularz(
  'Formularz o zajecia sportowo-rekreacyjne',
  'Zajecia sportowo-rekreacyjne',
  'kwota,placowka sportowa,ilosc godzin w tygodniu',
  'float,string,int',
  '100,30,10');

  DodajFormularz(
  'Formularz o imprezy kulturalne',
  'Imprezy kulturalne',
  'kwota,nazwa imprezy,maksymalna ilosc osob,dlugosc trwania,przeznaczenie srodkow',
  'float,string,int,string,string',
  ',,,,');

  DodajFormularz(
  'Formularz o zapomoge',
  'Zapomogi',
  'kwota,powod,przeznaczenie srodkow',
  'float,string,string',
  '100,40,50');

  DodajFormularz(
  'Formularz o pomoc mieszkaniowa',
  'Pomoc mieszkaniowa',
  'kwota,forma pomocy,powod',
  'float,string,string',
  '100,30,100');
COMMIT;
END;
/

--Dodanie wnioskow
--/
BEGIN
INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (1, 'Oczekujacy', TO_DATE('2023-06-10', 'YYYY-MM-DD'), '1:1000;2:Egipt;3:19.07.2023-27.07.2023;4:1;', 4, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (2, 'Oczekujacy', TO_DATE('2023-05-21', 'YYYY-MM-DD'), '5:150;6:Centrum Aktywizacji i Integracji Spoleczne;7:2;', 5, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (3, 'Oczekujacy', TO_DATE('2023-06-12', 'YYYY-MM-DD'), '8:500;9:Wieczor poezji;10:50;11:5;12:Wynajem sali, oplacenie poczestunku;', 6, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (1, 'Oczekujacy', TO_DATE('2023-04-10', 'YYYY-MM-DD'), '1:2000;2:Mazury;3:21.07.2023-27.07.2023;4:5;', 6, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (1, 'Oczekujacy', TO_DATE('2023-05-11', 'YYYY-MM-DD'), '1:3000;2:Egipt;3:10.09.2023-11.09.2023;4:2;', 6, NULL, NULL);

COMMIT;
END;
/




