--/
BEGIN
  DodajWnioskodawce(
    p_login            => 'marekm',
    p_haslo            => 'pog',
    p_imie             => 'Marek',
    p_nazwisko         => 'Most',
    p_pozycja          => 'Kierownik',
    p_pesel            => '12345678911',
    p_data_urodzenia   => TO_DATE('1990-01-02', 'YYYY-MM-DD')
  );
END;
/
INSERT INTO TYP_SRODKOW_ENCJA_SLOWNIKOWA (nazwa_funduszu) VALUES ('Fundusz 1');
--/
DECLARE
  v_nazwa_formularzu VARCHAR2(40) := 'Formularz 1';
  v_nazwa_funduszu VARCHAR2(30) := 'Fundusz 1';
  v_nazwy_pol VARCHAR2(200) := 'Pole 1,Pole 2,Pole 3';
  v_typy_danych VARCHAR2(200) := 'Typ 1,Typ 2,Typ 3';
  v_maksymalne_dlugosci VARCHAR2(200) := '10,20,';
BEGIN
  DodajFormularz(v_nazwa_formularzu, v_nazwa_funduszu, v_nazwy_pol, v_typy_danych, v_maksymalne_dlugosci);
END;
/
