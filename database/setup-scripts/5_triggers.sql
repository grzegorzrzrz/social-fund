connect c##develop/oracle
--/
CREATE OR REPLACE TRIGGER Trigger_ZmianaStatusuWniosku
AFTER UPDATE ON Wniosek
FOR EACH ROW
BEGIN
  INSERT INTO historia_statusow_wnioskow (
    data,
    wartosc_poprzednia_statusu,
    nowa_wartosc_statusu,
    wniosek_id_wniosku
  ) VALUES (
    SYSDATE,
    :OLD.status,
    :NEW.status,
    :NEW.id_wniosku
  );

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Rekord zostal dodany do historii zmian statusu wniosku.');
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    DBMS_OUTPUT.PUT_LINE('Wystapil blad podczas dodawania rekordu do historii zmian statusu wniosku: ' || SQLERRM);
END;
/
