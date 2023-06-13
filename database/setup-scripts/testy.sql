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
