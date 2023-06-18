-- Dodanie administratorow
--/
DECLARE
  v_id_uzytkownika NUMBER;
BEGIN
  INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Jan', 'D¹b', 'admin1', 'admin1')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Specjalista', 'Finanse');
  
    INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Katarzyna', 'Piec', 'admin2', 'admin2')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Specjalista', 'Kadry i p³ace');
  
      INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Bart³omiej', '¯bik', 'admin3', 'admin3')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Asystent', 'Kadry i p³ace');
END;
/
-- Dodanie uzytkownikow wnioskodawcow
--/
BEGIN
  DodajWnioskodawce(
p_login => 'marekm',
p_haslo => 'pog',
p_imie => 'Marek',
p_nazwisko => 'Most',
p_pozycja => 'Kierownik',
p_pesel => '12345678911',
p_data_urodzenia => TO_DATE('1990-01-02', 'YYYY-MM-DD')
);

DodajWnioskodawce(
p_login => 'kasiar',
p_haslo => 'hello123',
p_imie => 'Kasia',
p_nazwisko => 'Kowalska',
p_pozycja => 'Specjalista',
p_pesel => '98765432199',
p_data_urodzenia => TO_DATE('1985-06-12', 'YYYY-MM-DD')
);

DodajWnioskodawce(
p_login => 'adamw',
p_haslo => 'qwerty',
p_imie => 'Adam',
p_nazwisko => 'Wójcik',
p_pozycja => 'Analityk',
p_pesel => '45678912322',
p_data_urodzenia => TO_DATE('1988-09-25', 'YYYY-MM-DD')
);

DodajWnioskodawce(
p_login => 'magdab',
p_haslo => 'haslo123',
p_imie => 'Magda',
p_nazwisko => 'B³aszczyk',
p_pozycja => 'Dyrektor',
p_pesel => '11122233344',
p_data_urodzenia => TO_DATE('1976-03-14', 'YYYY-MM-DD')
);
END;
/

-- Dodanie typow srodkow
--/
BEGIN
  INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (NAZWA_FUNDUSZU)
  VALUES ('Wypoczynek wakacyjny');

  INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (NAZWA_FUNDUSZU)
  VALUES ('Zajêcia sportowo-rekreacyjne');

  INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (NAZWA_FUNDUSZU)
  VALUES ('Imprezy kulturalne');

  INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (NAZWA_FUNDUSZU)
  VALUES ('Zapomogi');

  INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (NAZWA_FUNDUSZU)
  VALUES ('Pomoc mieszkaniowa');
END;
/

-- Dodanie Wartosci funduszu
--/
BEGIN
  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Wypoczynek wakacyjny', 2022, 10000, 9000, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Zajêcia sportowo-rekreacyjne', 2022, 1000, 1000, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Imprezy kulturalne', 2022, 3000, 200, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Zapomogi', 2022, 20000, 20000, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Pomoc mieszkaniowa', 2022, 10000, 9000, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Wypoczynek wakacyjny', 2023, 10000, 0, 1000);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Zajêcia sportowo-rekreacyjne', 2023, 0, 1000, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Imprezy kulturalne', 2023, 3000, 0, 2800);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Zapomogi', 2023, 20000, 0, NULL);

  INSERT INTO OPIS_FUNDUSZU (TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, ROK, KWOTA_PRZYZNANA, KWOTA_UZYTA, KWOTA_Z_POPRZEDNIEGO_ROKU)
  VALUES ('Pomoc mieszkaniowa', 2023, 1000, 0, 1000);
END;
/
-- Dodanie formularzy
--/
BEGIN
  DodajFormularz(
  'Formularz o wypoczynek wakacyjny',
  'Wypoczynek wakacyjny',
  'kwota,miejsce wakacji,zakres czasowy wypoczynku,iloœæ osób',
  'float,string,string,int',
  '100,50,20,10');
  
    DodajFormularz(
  'Formularz o zajêcia sportowo-rekreacyjne',
  'Zajêcia sportowo-rekreacyjne',
  'kwota,placówka sportowa,iloœæ godzin w tygodniu',
  'float,string,int',
  '100,30,10');
  
  DodajFormularz(
  'Formularz o imprezy kulturalne',
  'Imprezy kulturalne',
  'kwota,nazwa imprezy,maksymalna iloœæ osób,d³ugoœæ trwania,przeznaczenie œrodków',
  'float,string,int,string,string',
  ',,,,');
  
  DodajFormularz(
  'Formularz o zapomoge',
  'Zapomogi',
  'kwota,powód,przeznaczenie œrodków',
  'float,string,string',
  '100,40,50');
  
  DodajFormularz(
  'Formularz o pomoc mieszkaniow¹',
  'Pomoc mieszkaniowa',
  'kwota,forma pomocy,powód',
  'float,string,string',
  '100,30,100');
  
END;
/





