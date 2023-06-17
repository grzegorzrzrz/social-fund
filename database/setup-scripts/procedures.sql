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
  p_id_wnioskodawcy      IN NUMBER
  p_dochod               IN NUMBER
) AS
v_data_zlozenia DATE := SYSDATE;
BEGIN
  INSERT INTO oswiadczenie_zarobkowe (
    dochod_na_czlonka_rodziny,
    data,
    data_zlozenia,
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
  p_id_wniosku      IN NUMBER
  p_status               IN VARCHAR2(15)
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
  p_id_wniosku      IN NUMBER
  p_wartosc               IN NUMBER
) AS
  v_nazwa_funduszu Wniosek.typ_srodkow_encja_slownikowa_nazwa_funduszu%TYPE;
  v_kwota_przyznana opis_funduszu.kwota_przyznana%TYPE;
  v_kwota_uzyta opis_funduszu.kwota_uzyta%TYPE;
  v_data_zlozenia DATE := SYSDATE;
  v_rok NUMBER;
BEGIN

SELECT typ_srodkow_encja_slownikowa_nazwa_funduszu into v_nazwa_funduszu FROM Wniosek where id_wniosku = p_id_wniosku;

v_rok := EXTRACT(YEAR FROM SYSDATE);

SELECT kwota_uzyta, kwota_przyznana into v_kwota_uzyta, v_kwota_przyznana FROM opis_funduszu_pk
WHERE typ_srodkow_encja_slownikowa_nazwa_funduszu = v_nazwa_funduszu AND Rok = v_rok;

IF (v_kwota_uzyta + p_wartosc) > v_kwota_przyznana THEN
    RAISE_APPLICATION_ERROR(-20001, 'Przekroczono przyznane środki.');

v_kwota_uzyta := v_kwota_uzyta + p_wartosc;
UPDATE opis_funduszu
SET kwota_uzyta = v_kwota_uzyta
WHERE typ_srodkow_encja_slownikowa_nazwa_funduszu = v_nazwa_funduszu AND Rok = v_rok;

INSERT INTO wyplata (
  wartosc_wyplaty,
  data_wyplaty,
  ) VALUES (
    p_wartosc,
    v_data_zlozenia
  );


  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Pomyslnie wyplacono srodki.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas wyplacania srodkow: ' || SQLERRM);
END;
/