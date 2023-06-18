connect c##develop/oracle

CREATE SEQUENCE adres_korespondencji_adres_kor START WITH 1 NOCACHE ORDER;

CREATE TABLE adres_korespondencji (
    id_adress               NUMBER NOT NULL,
    ulica                   VARCHAR2(20) NOT NULL,
    numer_domu              NUMBER NOT NULL,
    numer_mieszkania        NUMBER,
    kod_pocztowy            VARCHAR2(20),
    adres_korespondencji_id NUMBER NOT NULL
)
LOGGING;

ALTER TABLE adres_korespondencji ADD CONSTRAINT adres_korespondencji_pk PRIMARY KEY ( adres_korespondencji_id );


--  ERROR: UK name length exceeds maximum allowed length(30)
ALTER TABLE adres_korespondencji ADD CONSTRAINT adres_korespondencji_id_adress_un UNIQUE ( id_adress );

CREATE TABLE czlonkowie_rodziny (
    id_czlonkowie_rodziny       NUMBER NOT NULL,
    imie                        VARCHAR2(20) NOT NULL,
    nazwisko                    VARCHAR2(20) NOT NULL,
    data_urodzenia              DATE NOT NULL,
    stopien_pokrewienstwa       VARCHAR2(20) NOT NULL,
    wnioskodawcy_id_uzytkownika NUMBER NOT NULL
)
LOGGING;

ALTER TABLE czlonkowie_rodziny ADD CONSTRAINT czlonkowie_rodziny_pk PRIMARY KEY ( id_czlonkowie_rodziny );

CREATE TABLE historia_statusow_wnioskow (
    id_historii                NUMBER NOT NULL,
    data                       DATE NOT NULL,
    wartosc_poprzednia_statusu VARCHAR2(15) NOT NULL,
    nowa_wartosc_statusu       VARCHAR2(15) NOT NULL,
    wniosek_id_wniosku         NUMBER NOT NULL
)
LOGGING;

ALTER TABLE historia_statusow_wnioskow ADD CONSTRAINT historia_statusow_wnioskow_pk PRIMARY KEY ( id_historii );

CREATE TABLE opis_funduszu (
--  ERROR: Column name length exceeds maximum allowed length(30)
    typ_srodkow_encja_slownikowa_nazwa_funduszu VARCHAR2(30) NOT NULL,
    rok                                         NUMBER NOT NULL,
    kwota_przyznana                             NUMBER NOT NULL,
    kwota_uzyta                                 NUMBER NOT NULL,
    kwota_z_poprzedniego_roku                   NUMBER
)
LOGGING;

ALTER TABLE opis_funduszu ADD CONSTRAINT opis_funduszu_pk PRIMARY KEY ( rok );

CREATE TABLE oswiadczenie_zarobkowe (
    dochod_na_czlonka_rodziny   NUMBER NOT NULL,
    data                        DATE NOT NULL,
    wnioskodawcy_id_uzytkownika NUMBER NOT NULL
)
LOGGING;

ALTER TABLE oswiadczenie_zarobkowe ADD CONSTRAINT oswiadczenie_zarobkowe_pk PRIMARY KEY ( data,
                                                                                          wnioskodawcy_id_uzytkownika );

CREATE TABLE pola_formularzu (
    id_pola                        NUMBER NOT NULL,
    nazwa_pola                     VARCHAR2(20) NOT NULL,
    typ_pol_formularzu_id_typu     NUMBER NOT NULL,
    typ_formularzu_id_formularzu NUMBER NOT NULL
)
LOGGING;

ALTER TABLE pola_formularzu ADD CONSTRAINT pola_formularzu_pk PRIMARY KEY ( id_pola );

--  ERROR: Table name length exceeds maximum allowed length(30)
CREATE TABLE polaczenie_pomiedzy_formularzami_a_typem_srodkow (
--  ERROR: Column name length exceeds maximum allowed length(30)
    typ_srodkow_encja_slownikowa_nazwa_funduszu VARCHAR2(30) NOT NULL,
    typ_formularzu_id_formularzu             NUMBER NOT NULL
)
LOGGING;

--  ERROR: PK name length exceeds maximum allowed length(30)
ALTER TABLE polaczenie_pomiedzy_formularzami_a_typem_srodkow ADD CONSTRAINT polaczenie_pomiedzy_formularzami_a_typem_srodkow_pk PRIMARY
KEY ( typ_srodkow_encja_slownikowa_nazwa_funduszu,
                                             typ_formularzu_id_formularzu
                                                                                                                                );

CREATE TABLE rozpatrujacy (
    id_uzytkownika   NUMBER NOT NULL,
    pozycja_w_firmie VARCHAR2(20) NOT NULL,
    dzial_firmy      VARCHAR2(20) NOT NULL
)
LOGGING;

ALTER TABLE rozpatrujacy ADD CONSTRAINT rozpatrujacy_pk PRIMARY KEY ( id_uzytkownika );

CREATE TABLE typ_formularzu (
    nazwa_formularzu VARCHAR2(40) NOT NULL,
    id_formularzu NUMBER NOT NULL,
    czy_aktywny NUMBER(1) DEFAULT 1 NOT NULL
)
LOGGING;

ALTER TABLE typ_formularzu ADD CONSTRAINT typ_formularzu_pk PRIMARY KEY ( id_formularzu );

CREATE TABLE typ_pol_formularzu (
    id_typu            NUMBER NOT NULL,
    typ_danej          VARCHAR2(50) NOT NULL,
    maksymalna_dlugosc NUMBER
)
LOGGING;

ALTER TABLE typ_pol_formularzu ADD CONSTRAINT typ_pol_formularzu_pk PRIMARY KEY ( id_typu );

CREATE TABLE typ_srodkow_encja_slownikowa (
    nazwa_funduszu VARCHAR2(30) NOT NULL
)
LOGGING;

--  ERROR: PK name length exceeds maximum allowed length(30)
ALTER TABLE typ_srodkow_encja_slownikowa ADD CONSTRAINT typ_srodkow_encja_slownikowa_pk PRIMARY KEY ( nazwa_funduszu );

CREATE TABLE uzytkownicy (
    id_uzytkownika NUMBER NOT NULL,
    imie           VARCHAR2(20) NOT NULL,
    nazwisko       VARCHAR2(20) NOT NULL,
    haslo          VARCHAR2(40) NOT NULL,
    login          VARCHAR2(30) NOT NULL)
LOGGING;

ALTER TABLE uzytkownicy ADD CONSTRAINT uzytkownicy_pk PRIMARY KEY ( id_uzytkownika );
ALTER TABLE uzytkownicy ADD CONSTRAINT unikalny_login UNIQUE (login);
CREATE TABLE wniosek (
--  ERROR: Column name length exceeds maximum allowed length(30)
    typ_srodkow_encja_slownikowa_nazwa_funduszu VARCHAR2(30) NOT NULL,
    id_wniosku                                  NUMBER NOT NULL,
    status                                      VARCHAR2(15) NOT NULL,
    data_zlozenia                               DATE NOT NULL,
    zawartosc_formularza                        VARCHAR2(300) NOT NULL,
    wnioskodawcy_id_uzytkownika                 NUMBER NOT NULL,
    rozpatrujacy_id_uzytkownika                 NUMBER,
    wyplata_id_wyplaty                          NUMBER
)
LOGGING;

CREATE UNIQUE INDEX wniosek_idx ON
    wniosek (
        wyplata_id_wyplaty
    ASC );

ALTER TABLE wniosek ADD CONSTRAINT wniosek_pk PRIMARY KEY ( id_wniosku );

CREATE TABLE wnioskodawcy (
    id_uzytkownika                               NUMBER NOT NULL,
    firma                                        VARCHAR2(30),
    pesel                                        VARCHAR2(30),
    data_urodzenia                               DATE NOT NULL,
    numer_konta_bankowego                        NUMBER,
--  ERROR: Column name length exceeds maximum allowed length(30)
    adres_korespondencji_adres_korespondencji_id NUMBER
)
LOGGING;

ALTER TABLE wnioskodawcy ADD CONSTRAINT wnioskodawcy_pk PRIMARY KEY ( id_uzytkownika );

CREATE TABLE wyplata (
    id_wyplaty      NUMBER NOT NULL,
    wartosc_wyplaty NUMBER NOT NULL,
    data_wyplaty    DATE NOT NULL
)
LOGGING;

ALTER TABLE wyplata ADD CONSTRAINT wyplata_pk PRIMARY KEY ( id_wyplaty );

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE czlonkowie_rodziny
    ADD CONSTRAINT czlonkowie_rodziny_wnioskodawcy_fk FOREIGN KEY ( wnioskodawcy_id_uzytkownika )
        REFERENCES wnioskodawcy ( id_uzytkownika )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE historia_statusow_wnioskow
    ADD CONSTRAINT historia_statusow_wnioskow_wniosek_fk FOREIGN KEY ( wniosek_id_wniosku )
        REFERENCES wniosek ( id_wniosku )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE opis_funduszu
    ADD CONSTRAINT opis_funduszu_typ_srodkow_encja_slownikowa_fk FOREIGN KEY ( typ_srodkow_encja_slownikowa_nazwa_funduszu )
        REFERENCES typ_srodkow_encja_slownikowa ( nazwa_funduszu )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE oswiadczenie_zarobkowe
    ADD CONSTRAINT oswiadczenie_zarobkowe_wnioskodawcy_fk FOREIGN KEY ( wnioskodawcy_id_uzytkownika )
        REFERENCES wnioskodawcy ( id_uzytkownika )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE pola_formularzu
    ADD CONSTRAINT pola_formularzu_typ_formularzu_fk FOREIGN KEY ( typ_formularzu_id_formularzu )
        REFERENCES typ_formularzu ( id_formularzu )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE pola_formularzu
    ADD CONSTRAINT pola_formularzu_typ_pol_formularzu_fk FOREIGN KEY ( typ_pol_formularzu_id_typu )
        REFERENCES typ_pol_formularzu ( id_typu )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE polaczenie_pomiedzy_formularzami_a_typem_srodkow
    ADD CONSTRAINT polaczenie_pomiedzy_formularzami_a_typem_srodkow_typ_formularzu_fk FOREIGN KEY ( typ_formularzu_id_formularzu )
        REFERENCES typ_formularzu ( id_formularzu )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE polaczenie_pomiedzy_formularzami_a_typem_srodkow
    ADD CONSTRAINT polaczenie_pomiedzy_formularzami_a_typem_srodkow_typ_srodkow_encja_slownikowa_fk FOREIGN KEY ( typ_srodkow_encja_slownikowa_nazwa_funduszu
    )
        REFERENCES typ_srodkow_encja_slownikowa ( nazwa_funduszu )
    NOT DEFERRABLE;

ALTER TABLE rozpatrujacy
    ADD CONSTRAINT rozpatrujacy_uzytkownicy_fk FOREIGN KEY ( id_uzytkownika )
        REFERENCES uzytkownicy ( id_uzytkownika )
    NOT DEFERRABLE;

ALTER TABLE wniosek
    ADD CONSTRAINT wniosek_rozpatrujacy_fk FOREIGN KEY ( rozpatrujacy_id_uzytkownika )
        REFERENCES rozpatrujacy ( id_uzytkownika )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE wniosek
    ADD CONSTRAINT wniosek_typ_srodkow_encja_slownikowa_fk FOREIGN KEY ( typ_srodkow_encja_slownikowa_nazwa_funduszu )
        REFERENCES typ_srodkow_encja_slownikowa ( nazwa_funduszu )
    NOT DEFERRABLE;

ALTER TABLE wniosek
    ADD CONSTRAINT wniosek_wnioskodawcy_fk FOREIGN KEY ( wnioskodawcy_id_uzytkownika )
        REFERENCES wnioskodawcy ( id_uzytkownika )
    NOT DEFERRABLE;

ALTER TABLE wniosek
    ADD CONSTRAINT wniosek_wyplata_fk FOREIGN KEY ( wyplata_id_wyplaty )
        REFERENCES wyplata ( id_wyplaty )
    NOT DEFERRABLE;

--  ERROR: FK name length exceeds maximum allowed length(30)
ALTER TABLE wnioskodawcy
    ADD CONSTRAINT wnioskodawcy_adres_korespondencji_fk FOREIGN KEY ( adres_korespondencji_adres_korespondencji_id )
        REFERENCES adres_korespondencji ( adres_korespondencji_id )
    NOT DEFERRABLE;

ALTER TABLE wnioskodawcy
    ADD CONSTRAINT wnioskodawcy_uzytkownicy_fk FOREIGN KEY ( id_uzytkownika )
        REFERENCES uzytkownicy ( id_uzytkownika )
    NOT DEFERRABLE;

CREATE OR REPLACE TRIGGER adres_korespondencji_adres_kor BEFORE
    INSERT ON adres_korespondencji
    FOR EACH ROW
    WHEN ( new.adres_korespondencji_id IS NULL )
BEGIN
    :new.adres_korespondencji_id := adres_korespondencji_adres_kor.nextval;
END;