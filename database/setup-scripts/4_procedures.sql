--/
CREATE OR REPLACE PROCEDURE DodajWnioskodawce (
  p_login            IN VARCHAR2,
  p_haslo            IN VARCHAR2,
  p_imie             IN VARCHAR2,
  p_nazwisko         IN VARCHAR2,
  p_pozycja          IN VARCHAR2,
  p_pesel            IN VARCHAR2,
  p_data_urodzenia   IN DATE
) AS
  v_id_uzytkownika Uzytkownicy.id_uzytkownika%TYPE;
BEGIN
  INSERT INTO Uzytkownicy (imie, nazwisko, haslo, login)
  VALUES (p_imie, p_nazwisko, p_haslo, p_login)
  RETURNING id_uzytkownika INTO v_id_uzytkownika;

  INSERT INTO Wnioskodawcy (id_uzytkownika, firma, pesel, data_urodzenia)
  VALUES (v_id_uzytkownika, p_pozycja, p_pesel, p_data_urodzenia);

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Nowy uzytkownik wnioskodawca zostal dodany do bazy danych.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas dodawania uzytkownika: ' || SQLERRM);
END;
/

--/
CREATE OR REPLACE PROCEDURE DodajWniosek (
  p_nazwa_funduszu       IN VARCHAR2,
  p_zawartosc_formularza IN VARCHAR2,
  p_id_wnioskodawcy      IN NUMBER
) AS
v_data_zlozenia DATE := SYSDATE;
  v_status        VARCHAR2(20) := 'Oczekujacy';
BEGIN
  INSERT INTO Wniosek (
    TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU,
    status,
    data_zlozenia,
    zawartosc_formularza,
    wnioskodawcy_id_uzytkownika
  ) VALUES (
    p_nazwa_funduszu,
    v_status,
    v_data_zlozenia,
    p_zawartosc_formularza,
    p_id_wnioskodawcy
  );

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Wniosek zostal utworzony.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas tworzenia wniosku: ' || SQLERRM);
END;
/

--/
CREATE OR REPLACE PROCEDURE DodajDochodWnioskodawcy (
  p_id_wnioskodawcy      IN NUMBER,
  p_dochod               IN NUMBER
) AS
v_data_zlozenia DATE := SYSDATE;
BEGIN
  INSERT INTO oswiadczenie_zarobkowe (
    dochod_na_czlonka_rodziny,
    data,
    wnioskodawcy_id_uzytkownika
  ) VALUES (
    p_dochod,
    v_data_zlozenia,
    p_id_wnioskodawcy
  );

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Oswiadczenie zarobkowe zostalo dodane.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas dodawania oswiadczenia zarobkowego: ' || SQLERRM);
END;
/
--/
CREATE OR REPLACE PROCEDURE UstawStatusWniosku (
  p_id_wniosku      IN NUMBER,
  p_status               IN VARCHAR2,
  p_id_rozpatrujacego IN NUMBER
) AS
BEGIN
  UPDATE Wniosek
SET rozpatrujacy_id_uzytkownika = p_id_rozpatrujacego, status= p_status
WHERE id_wniosku = p_id_wniosku;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Pomyslnie zmieniono status wniosku.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas zmieniania statusu wniosku: ' || SQLERRM);
END;
/
--/
CREATE OR REPLACE PROCEDURE WyplacSrodki (
  p_id_wniosku      IN NUMBER,
  p_wartosc         IN NUMBER
) AS
  v_nazwa_funduszu Wniosek.typ_srodkow_encja_slownikowa_nazwa_funduszu%TYPE;
  v_kwota_przyznana opis_funduszu.kwota_przyznana%TYPE;
  v_kwota_uzyta opis_funduszu.kwota_uzyta%TYPE;
  v_data_zlozenia DATE := SYSDATE;
  v_rok NUMBER;
BEGIN
  SELECT typ_srodkow_encja_slownikowa_nazwa_funduszu
  INTO v_nazwa_funduszu
  FROM Wniosek
  WHERE id_wniosku = p_id_wniosku;

  v_rok := EXTRACT(YEAR FROM SYSDATE);

  SELECT kwota_uzyta, kwota_przyznana
  INTO v_kwota_uzyta, v_kwota_przyznana
  FROM opis_funduszu
  WHERE typ_srodkow_encja_slownikowa_nazwa_funduszu = v_nazwa_funduszu
    AND Rok = v_rok;

  IF (v_kwota_uzyta + p_wartosc) > v_kwota_przyznana THEN
    RAISE_APPLICATION_ERROR(-20001, 'Przekroczono przyznane środki.');
  END IF;

  v_kwota_uzyta := v_kwota_uzyta + p_wartosc;

  UPDATE opis_funduszu
  SET kwota_uzyta = v_kwota_uzyta
  WHERE typ_srodkow_encja_slownikowa_nazwa_funduszu = v_nazwa_funduszu
    AND Rok = v_rok;

  INSERT INTO wyplata (wartosc_wyplaty, data_wyplaty)
  VALUES (p_wartosc, v_data_zlozenia);

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Pomyślnie wypłacono środki.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystąpił błąd podczas wypłacania środków: ' || SQLERRM);
END;
/

--/
CREATE OR REPLACE PROCEDURE DezaktywujFormularz (
  p_id_formularzu      IN NUMBER
) AS
BEGIN

UPDATE typ_formularzu
SET czy_aktywny = 0
WHERE id_formularzu = p_id_formularzu;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Pomyslnie dezaktywowano formularz.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas dezaktywowania formularzu: ' || SQLERRM);
END;
/
connect c##develop/oracle

--/
CREATE OR REPLACE PROCEDURE DodajFormularz (
  p_nazwa_formularzu      IN VARCHAR2,
  p_nazwa_funduszu        IN VARCHAR2,
  p_nazwy_pol             IN VARCHAR2,
  p_typy_danych             IN VARCHAR2,
  p_maksymalne_dlugosci     IN VARCHAR2

) AS
v_nazwy_pol SYS.ODCIVARCHAR2LIST := SYS.ODCIVARCHAR2LIST();
v_typy_danych SYS.ODCIVARCHAR2LIST := SYS.ODCIVARCHAR2LIST();
v_maksymalne_dlugosci SYS.ODCIVARCHAR2LIST := SYS.ODCIVARCHAR2LIST();
v_separator VARCHAR2(10) := ',';
v_id_formularzu typ_formularzu.id_formularzu%TYPE;
v_id_typu typ_pol_formularzu.id_typu%TYPE;
BEGIN
FOR i IN 1 .. REGEXP_COUNT(p_nazwy_pol, v_separator) + 1 LOOP
        v_nazwy_pol.EXTEND;
        v_nazwy_pol(i) := REGEXP_SUBSTR(p_nazwy_pol, '[^' || v_separator || ']+', 1, i);
    END LOOP;

FOR i IN 1 .. REGEXP_COUNT(p_typy_danych, v_separator) + 1 LOOP
        v_typy_danych.EXTEND;
        v_typy_danych(i) := REGEXP_SUBSTR(p_typy_danych, '[^' || v_separator || ']+', 1, i);
    END LOOP;

FOR i IN 1 .. REGEXP_COUNT(p_maksymalne_dlugosci, v_separator) + 1 LOOP
        v_maksymalne_dlugosci.EXTEND;
        v_maksymalne_dlugosci(i) := REGEXP_SUBSTR(p_maksymalne_dlugosci, '[^' || v_separator || ']+', 1, i);
    END LOOP;

INSERT INTO typ_formularzu (nazwa_formularzu) VALUES (p_nazwa_formularzu) RETURNING id_formularzu INTO v_id_formularzu;
INSERT INTO polaczenie_pomiedzy_formularzami_a_typem_srodkow (typ_srodkow_encja_slownikowa_nazwa_funduszu, typ_formularzu_id_formularzu)
VALUES (p_nazwa_funduszu, v_id_formularzu);
FOR i IN 1 .. v_nazwy_pol.COUNT LOOP
INSERT INTO typ_pol_formularzu (typ_danej, maksymalna_dlugosc) VALUES (v_typy_danych(i), TO_NUMBER(v_maksymalne_dlugosci(i))) RETURNING id_typu INTO v_id_typu;
INSERT INTO pola_formularzu (nazwa_pola, typ_pol_formularzu_id_typu, typ_formularzu_id_formularzu) VALUES (v_nazwy_pol(i), v_id_typu, v_id_formularzu);
END LOOP;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Pomyslnie dodano formularz.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas dodawania formularza: ' || SQLERRM);
END;
/