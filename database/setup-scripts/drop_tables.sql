--/
BEGIN
  FOR cur_tab IN (SELECT table_name FROM all_tables WHERE owner = 'C##DEVELOP') LOOP
    EXECUTE IMMEDIATE 'DROP TABLE ' || cur_tab.table_name || ' CASCADE CONSTRAINTS';
  END LOOP;
END;
/
