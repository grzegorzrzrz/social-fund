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
VALUES (1, 'Oczekujacy', TO_DATE('2023-06-10', 'YYYY-MM-DD'),
        'string:1000:kwota:100;string:Egipt:miejsce wakacji:50;string:19.07.2023-27.07.2023:zakres czasowy wypoczynku:20;string:1:ilosc osob:', 4, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (2, 'Oczekujacy', TO_DATE('2023-05-21', 'YYYY-MM-DD'),
        'string:150:kwota:30;string:Centrum Aktywizacji i Integracji Spoleczne:placowka sportowa:;string:2:ilosc godzin w tygodniu:', 5, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (3, 'Oczekujacy', TO_DATE('2023-06-12', 'YYYY-MM-DD'),
        'string:500:kwota:;string:Wieczor poezji:nazwa imprezy:;string:50:maksymalna ilosc osob:;string:5:dlugosc trwania:40;string:Wynajem sali, oplacenie poczestunku:przeznaczenie srodkow:100', 6, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (1, 'Oczekujacy', TO_DATE('2023-04-10', 'YYYY-MM-DD'),
        'string:2000:kwota:100;string:Mazury:miejsce wakacji:50;string:21.07.2023-27.07.2023:zakres czasowy wypoczynku:20;string:5:ilosc osob:10', 6, NULL, NULL);

INSERT INTO wniosek (typ_formularzu_id_formularzu, status, data_zlozenia, zawartosc_formularza, wnioskodawcy_id_uzytkownika, rozpatrujacy_id_uzytkownika, wyplata_id_wyplaty)
VALUES (1, 'Oczekujacy', TO_DATE('2023-05-11', 'YYYY-MM-DD'),
        'string:3000:kwota:100;string:Egipt:miejsce wakacji:50;string:10.09.2023-11.09.2023:20zakres czasowy wypoczynku:;string:2:ilosc osob:10', 6, NULL, NULL);

COMMIT;
END;
/




