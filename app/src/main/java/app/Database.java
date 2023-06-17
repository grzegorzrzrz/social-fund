package app;

import classes.*;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;

public class Database {
    private final String DBURL;
    private final String DBUSERNAME;
    private final String DBPASSWORD;
    private Connection con;
    private Statement stmt;

    public Database(String url, String user, String password)
    {
        DBURL = url;
        DBUSERNAME = user;
        DBPASSWORD = password;
    }

    /**
     * @param sql sql query
     * @return Result of sql query, connection need to be closed after extracting result
     */
    private ResultSet select(String sql) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param dml insert or update operation to be executed in database
     */
    private void dml(String dml) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            stmt.executeUpdate(dml);
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param procedure procedure to be executed in database
     */
    private void procedure(String procedure) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            stmt.execute(procedure);
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param condName name of data type in database
     * @param data fragment of searched data
     * @return condition for sql query
     */
    private String addCondition(String condName, String data) {
        return condName + " like '%" + data + "%'";
    }

     /**
     * @param userID id of user
     * @return Array of integers with all form types ids
     */
    public ArrayList<Integer> getApplicationsByUserID(int userID) {
        String sql = "select id_wniosku" +
                "from WNIOSEK" +
                "where id_uzytkownika=" + userID;
        return getApplicationIDs(sql);
    }

    /**
     * @return Array of integers with all form types ids
     */
    public ArrayList<Integer> getPendingApplications() {
        String sql = "select id_wniosku" +
                "from WNIOSEK" +
                "where status='Rozpatrywany'";
        return getApplicationIDs(sql);
    }

    /**
     * @return Array of integers with all form types ids
     */
    public void deactivateFormType(int formID) {
        String sql = "UPDATE typ SET status = 'Nieaktywny' WHERE id_typu_wniosku = " + formID;
        dml(sql);
    }

    /**
     * @param sql sql query
     * @return Array of integers with all form types ids
     */
    private ArrayList<Integer> getApplicationIDs(String sql) {
        try {
            ResultSet rs = select(sql);
            ArrayList<Integer> formTypes = new ArrayList<>();
            while (rs.next()) {
                formTypes.add(rs.getInt(1));
            }
            rs.close();
            stmt.close();
            con.close();
            return formTypes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param applicationID id of application
     * @return application with given id
     */
    public Application getApplicationInfo(int applicationID) {
        String sql = "SELECT * FROM WNIOSEK WHERE id_wniosku = " + applicationID;
        return getApplicationInfo(sql);
    }

    /**
     * @param applicationID id of application
     * @param status status of application
     * @return application with given id
     */
    public Application getApplicationInfo(int applicationID, String status) {
        String sql = "SELECT * FROM WNIOSEK WHERE id_wniosku = " + applicationID + " AND status = '" + status + "'";
        return getApplicationInfo(sql);
    }

    /**
     * @param applicationID id of application
     * @param startDate start date of application
     * @param endDate end date of application
     * @return application with given id
     */
    public Application getApplicationInfo(int applicationID, Date startDate, Date endDate) {
        String sql = "SELECT * FROM WNIOSEK WHERE id_wniosku = " + applicationID +  "' AND data_zlozenia >= '" +
                startDate + "' AND data_zlozenia <= '" + endDate + "'";
        return getApplicationInfo(sql);
    }

    private Application getApplicationInfo(String sql) {
        try {
            ResultSet rs = select(sql);
            rs.next();
            Application application = getApplicationFromResult(rs);
            rs.close();
            stmt.close();
            con.close();
            return application;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param rs result from sql query
     * @return list of applications
     * @throws SQLException wrong sql query
     */
    private Application getApplicationFromResult(ResultSet rs) throws SQLException {
        String fund = rs.getString(1);
        int applicationID = rs.getInt(2);
        String status = rs.getString(3);
        Date creationDate = rs.getDate(4);;
        Form form = getFormFromString(rs.getString(5));
        Application application = new Application(fund, applicationID, status, creationDate, form);

        return application;
    }

    /**
     * Decodes encoded application form
     * @param encodedFields encoded form
     * @return decoded form
     */
    private Form getFormFromString(String encodedFields) throws SQLException {
        ArrayList<FormField> fields = new ArrayList<>();
        String[] fieldsArray = encodedFields.split(";");
        int formTypeID = Integer.parseInt(fieldsArray[0]);
        String  formName = getFormName(formTypeID);

        for (int i = 1; i < fieldsArray.length; i++) {
            String[] fieldParts = fieldsArray[i].split(":");
            int fieldID = Integer.parseInt(fieldParts[0]);
            String value = fieldParts[1];
            String name = fieldParts[2];
            String type = fieldParts[3];
            int maximumLength = Integer.parseInt(fieldParts[4]);
            FormField formField = new FormField(fieldID, name, type, value, maximumLength);
            fields.add(formField);
        }
        return new Form(1, formName, fields);
    }

    /**
     * @return array of Form objects
     */
    private ArrayList<Form> getForms() {
        String sql = "select * from typ_formularzu";
        try {
            ResultSet rs = select(sql);
            ArrayList<Form> forms = new ArrayList<>();
            while (rs.next()) {
                int formTypeID = rs.getInt(1);
                String formName = rs.getString(2);
                ArrayList<FormField> formFields = getFormFields(formTypeID);
                forms.add(new Form(formTypeID, formName, formFields));
            }
            rs.close();
            stmt.close();
            con.close();
            return forms;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param formTypeID id of form type
     * @return array of FormField objects
     */
    private ArrayList<FormField>  getFormFields(int formTypeID) {
        String sql = "select pf.id_pola, pf.nazwa_pola, tpf.typ_danej,  tpf.maksymalna_dlugosc from pola_formularzu pf join typ_pol_formularzu tpf on" +
                " pf.typ_pol_formularzu_id_typu = tpf.typ_pol_formularzu where pf.typ_formularzu_id_formularzu = " + formTypeID;
        try {
            ResultSet rs = select(sql);
            ArrayList<FormField> formFields = new ArrayList<>();
            while (rs.next()) {
                int fieldID = rs.getInt(1);
                String name = rs.getString(2);
                String type = rs.getString(3);
                int maxLength = rs.getInt(4);
                String value = "";
                formFields.add(new FormField(fieldID, name, type, value, maxLength));
            }
            rs.close();
            stmt.close();
            con.close();
            return formFields;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param application application to be added to database
     */
    private void addApplication(Application application, int userID) {
        String sql = "call DodajWniosek(" + application.getForm().getFormTypeID() + ", '" + getStringFromForm(application.getForm()) + "', " + userID + ")";
        procedure(sql);
    }

    /**
     * @param applicationID id of application
     * @param status status to be set
     */
    private void setApplicationStatus(int applicationID, String status){
        String sql = "call UstawStatusWniosku(" + applicationID + ", '" + status + "')";
        procedure(sql);
    }

    /**
     * @param fundName name of fund
     * @return current value of fund
     */
    private int getCurrentFundValue(int fundName){
        String sql = "select kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku from fundusz where typ_srodkow_encja_slownikowa_nazwa_funduszu = " + fundName;
        try {
            ResultSet rs = select(sql);
            rs.next();
            int value = rs.getInt(1) - rs.getInt(2) + rs.getInt(3);
            rs.close();
            stmt.close();
            con.close();
            return value;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param fundName name of fund
     * @param value value to be added
     */
    private void withdrawFunds(int fundName, int value){
        String sql = "call WyplacSrodki(" + fundName + ", " + value + ")";
        procedure(sql);
    }

    /**
     * @param form form to be encoded
     * @return encoded form
     */
    private String getStringFromForm(Form form) {
        String encodedFields = form.getFormTypeID() + ";";
        for (FormField field : form.getFields()) {
            encodedFields += field.getFieldID() + ":" + field.getValue() + ":" + field.getName() + ":" + field.getType() + ":" + field.getMaximumLength() + ";";
        }
        return encodedFields;
    }

    /**
     * @param formTypeID id of form
     * @return name of form
     * @throws SQLException wrong sql query
     */
    private String getFormName(int formTypeID) throws SQLException {
        String sql = "SELECT name FROM FormTypes WHERE formTypeID = " + formTypeID;
        ResultSet rs = select(sql);
        rs.next();
        return rs.getString(1);
    }

    /**
     * @param login user login
     * @param password user password
     * @return user with given login and password
     */
    private int getUserID(String login, String password) {
        String sql = "SELECT * FROM Users WHERE login = '" + login + "' AND password = '" + password + "'";
        try {
            ResultSet rs = select(sql);
            rs.next();
            int userID = rs.getInt(1);
            rs.close();
            stmt.close();
            con.close();
            return userID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param login user login
     * @param password user password
     * @param name user name
     * @param surname user surname
     * @param company user company
     * @param pesel user pesel
     * @param birthDate user birth date
     */
    private void addNewUser(String login, String password, String name, String surname, String company, String pesel,
                            String birthDate) {
        this.addNewUser(login, password, name, surname, company, pesel, birthDate, "");
    }

    /**
     * @param login user login
     * @param password user password
     * @param name user name
     * @param surname user surname
     * @param company user company
     * @param pesel user pesel
     * @param birthDate user birth date
     * @param accountNumber user account number
     */
    private void addNewUser(String login, String password, String name, String surname, String company, String pesel,
                            String birthDate, String accountNumber) {
        String procedure = "call DodajWnioskodawce('" + login + "', '" + password + "', '" + name + "', '" + surname + "', '" +
                company + "', '" + pesel + "', '" + birthDate + "', '" + accountNumber + "')";
        procedure(procedure);
    }

    /**
     * @param userID user id
     * @param income user income
     */
    private void addUserIncome(int userID, String income) {
        String procedure = "call DodajDochodWnioskodawcy(" + userID + ", '" + income + "')";
        procedure(procedure);
    }
}
