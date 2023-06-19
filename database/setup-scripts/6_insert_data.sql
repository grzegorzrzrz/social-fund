connect c##develop/oracle

-- Dodanie administratorow
--/
DECLARE
  v_id_uzytkownika NUMBER;
BEGIN
  INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Jan', 'Dab', 'admin1', 'admin1')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Specjalista', 'Finanse');

    INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Katarzyna', 'Piec', 'admin2', 'admin2')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Specjalista', 'Kadry i place');

      INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES ('Bartlomiej', 'Zbik', 'admin3', 'admin3')
  RETURNING id_uzytkownika INTO v_id_uzytkownika;
  INSERT INTO Rozpatrujacy (id_uzytkownika, pozycja_w_firmie, dzial_firmy)
  VALUES (v_id_uzytkownika, 'Asystent', 'Kadry i place');

-- Dodanie uzytkownikow wnioskodawcow

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
p_nazwisko => 'Wojcik',
p_pozycja => 'Analityk',
p_pesel => '45678912322',
p_data_urodzenia => TO_DATE('1988-09-25', 'YYYY-MM-DD')
);

DodajWnioskodawce(
p_login => 'magdab',
p_haslo => 'haslo123',
p_imie => 'Magda',
p_nazwisko => 'Blaszczyk',
p_pozycja => 'Dyrektor',
p_pesel => '11122233344',
p_data_urodzenia => TO_DATE('1976-03-14', 'YYYY-MM-DD')
);
COMMIT;
END;
/
--Dodanie oswiadczen zarobkowych
--/
BEGIN
INSERT INTO oswiadczenie_zarobkowe (dochod_na_czlonka_rodziny, data, wnioskodawcy_id_uzytkownika)
VALUES (5000, TO_DATE('2023-06-19', 'YYYY-MM-DD'), 4);

INSERT INTO oswiadczenie_zarobkowe (dochod_na_czlonka_rodziny, data, wnioskodawcy_id_uzytkownika)
VALUES (4000, TO_DATE('2023-06-20', 'YYYY-MM-DD'), 5);

INSERT INTO oswiadczenie_zarobkowe (dochod_na_czlonka_rodziny, data, wnioskodawcy_id_uzytkownika)
VALUES (6000, TO_DATE('2023-06-21', 'YYYY-MM-DD'), 6);
COMMIT;
END;
/