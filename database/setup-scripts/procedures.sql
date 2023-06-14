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
  p_id_wnioskodawcy      IN NUMBER,
  p_id_rozpatrujacy      IN NUMBER,
) AS
v_data_zlozenia DATE := SYSDATE;
  v_status        VARCHAR2(20) := 'Oczekujacy';
BEGIN
  INSERT INTO Wniosek (
    TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU,
    status,
    data_zlozenia,
    zawartosc_formularza,
    wnioskodawcy_id_uzytkownika,
    rozpatrujacy_id_uzytkownika,
  ) VALUES (
    p_nazwa_funduszu,
    v_status,
    v_data_zlozenia,
    p_zawartosc_formularza,
    p_id_wnioskodawcy,
    p_id_rozpatrujacy,
  );

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Wniosek zosta³ utworzony.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wyst¹pi³ b³¹d podczas tworzenia wniosku: ' || SQLERRM);
END;
/

