package app;

import classes.*;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

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
    private ResultSet Select(String sql) {
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
    private void Dml(String dml) {
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
    private void Procedure(String procedure) {
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
    private String AddCondition(String condName, String data) {
        return condName + " like '%" + data + "%'";
    }

     /**
     * @param user id of user
     * @return Array of integers with all form types ids
     */
    public Vector<Pair<Integer, Date>> GetApplicationsByUser(User user) {
        String sql = "select id_wniosku, data_zlozenia" +
                " from WNIOSEK" +
                " where wnioskodawcy_id_uzytkownika=" + user.getUserID();
        return GetApplicationsVector(sql);
    }

    /**
     * @return Array of integers with all form types ids
     */
    public Vector<Pair<Integer, Date>>  GetPendingApplications() {
        String sql = "select id_wniosku, data_zlozenia " +
                "from WNIOSEK" +
                " where status='Oczekujacy'";
        return GetApplicationsVector(sql);
    }

    /**
     * @return Array of integers with all form types ids
     */
    public String DisableForm(String formName) {
        String sql = "call DezaktywujFormularz ('" + formName + "')";
        Procedure(sql);
        return "Pomyslnie dezaktywowano formularz.";
    }

    /**
     * @param sql sql query
     * @return Array of integers with all form types ids
     */
    private Vector<Pair<Integer, Date>>  GetApplicationsVector(String sql) {
        try {
            ResultSet rs = Select(sql);
            Vector<Pair<Integer, Date>> applications = new Vector<Pair<Integer, Date>>();
            while (rs.next()) {
                applications.add(Pair.of(rs.getInt(1), rs.getDate(2)));
            }
            rs.close();
            stmt.close();
            con.close();
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param applicationID id of application
     * @return application with given id
     */
    public Application GetApplicationInfo(int applicationID) {
        String sql = "SELECT pol.TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, form.nazwa_formularzu, w.status, w.data_zlozenia," +
                " w.zawartosc_formularza, w.wnioskodawcy_id_uzytkownika, w.typ_formularzu_id_formularzu FROM WNIOSEK" +
                " w join typ_formularzu form on w.typ_formularzu_id_formularzu = form.id_formularzu" +
                " join polaczenie_pomiedzy_formularzami_a_typem_srodkow pol on form.id_formularzu = pol.typ_formularzu_id_formularzu" +
                " where w.id_wniosku = " + applicationID;

        Application application = GetApplicationInfo(sql).get(0);
        return application;
    }
    public Applicant GetApplicantInfo(int applicantID) {
        Applicant applicant = new Applicant();
        applicant.setId(applicantID);
        String sql = "SELECT * FROM wnioskodawcy WHERE id_uzytkownika = " + applicantID;
        GetApplicant(sql, applicant);
        sql = "SELECT dochod_na_czlonka_rodziny FROM oswiadczenie_zarobkowe WHERE wnioskodawcy_id_uzytkownika = " + applicantID;
        GetEarnings(sql, applicant);
        return applicant;
    }
    private void GetEarnings(String sql, Applicant applicant) {
        try {
            ResultSet rs = Select(sql);
            while (rs.next()) {
                applicant.setEarnings(rs.getInt(1));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void GetApplicant(String sql, Applicant applicant) {
        try {
            ResultSet rs = Select(sql);
            while (rs.next()) {
                applicant.setCompany(rs.getString(2));
                applicant.setPesel(rs.getString(3));
                applicant.setBirthDate(rs.getDate(4));
                applicant.setAccountNumber(rs.getInt(5));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param startDate start date of application
     * @param endDate end date of application
     * @param status status of application
     * @return applications with given id
     */
    public ArrayList<Application> GetApplicationInfo(Date startDate, Date endDate, String status) {
        String sql = "SELECT pol.TYP_SRODKOW_ENCJA_SLOWNIKOWA_NAZWA_FUNDUSZU, form.nazwa_formularzu, w.status, w.data_zlozenia, " +
                "w.zawartosc_formularza, w.wnioskodawcy_id_uzytkownika, w.typ_formularzu_id_formularzu FROM WNIOSEK" +
                " w join typ_formularzu form on w.typ_formularzu_id_formularzu = form.id_formularzu" +
                " join polaczenie_pomiedzy_formularzami_a_typem_srodkow pol on form.id_formularzu = pol.typ_formularzu_id_formularzu" +
                " where w.data_zlozenia >= TO_DATE('" + startDate + "','YYYY-MM-DD') AND w.data_zlozenia <= TO_DATE('" + endDate + "','YYYY-MM-DD')";
        if (!status.equals("Any")) {
            sql += " AND w.status = '" + status + "'";
        }
        return GetApplicationInfo(sql);
    }

    /**
     * @param startDate start date of application
     * @param endDate end date of application
     * @param status status of application
     * @param formName fund of application
     * @return report with all valid applications
     */
    public StringBuilder GenerateReport(Date startDate, Date endDate, String status, String formName) {
        ArrayList<Application> applications = GetApplicationInfo(startDate, endDate, status);
        StringBuilder report = new StringBuilder();
        report.append("Status,Data złożenia,Typ wniosku,Typ funduszu,Pola\n");
        for (Application application : applications) {
            if(formName.equals("All") || application.getForm().getName().equals(formName)) {
                report.append(application.getStatus());
                report.append(",");
                report.append(application.getCreationDate());
                report.append(",");
                report.append(application.getForm().getName());
                report.append(",");
                report.append(application.getForm().getFundName());
                report.append(",{");
                for (FormField field : application.getForm().getFields()) {
                    report.append(field.getName());
                    report.append(",");
                    report.append(field.getValue());
                    report.append(",");
                    report.append(field.getMaximumLength());
                    report.append(",");
                    report.append(field.getType());
                }
                report.append("}\n");
            }
        }
        return report;
    }

    /**
     * @param sql sql query
     * @return ArrayList<Application> list of applications
     */
    private ArrayList<Application> GetApplicationInfo(String sql) {
        try {
            ResultSet rs = Select(sql);
            ArrayList<Application> applications = new ArrayList<>();
            while (rs.next()) {
                applications.add(GetApplicationFromResult(rs));
            }
            rs.close();
            stmt.close();
            con.close();
            return applications;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param rs result from sql query
     * @return list of applications
     * @throws SQLException wrong sql query
     */
    private Application GetApplicationFromResult(ResultSet rs) throws SQLException {
        String fundName = rs.getString(1);
        String formName = rs.getString(2);
        String status = rs.getString(3);
        Date creationDate = rs.getDate(4);
        int applicantID = rs.getInt(6);
        int formTypeID = rs.getInt(6);
        Form form = new Form(formTypeID, formName, fundName, new ArrayList<>());
        GetFormFromString(form, rs.getString(5));
        Application application = new Application(GetApplicantInfo(applicantID), status, creationDate, form);
        return application;
    }

    /**
     * Decodes encoded application form
     * @param encodedFields encoded form
     * @return decoded form
     */
    private void GetFormFromString(Form form, String encodedFields) throws SQLException {
        ArrayList<FormField> fields = new ArrayList<>();
        String[] fieldsArray = encodedFields.split(";");

        for (int i = 0; i < fieldsArray.length; i++) {
            String[] fieldParts = fieldsArray[i].split(":");
            String type = fieldParts[0];
            String value = fieldParts[1];
            String name = fieldParts[2];
            int maximumLength = 0;
            if (fieldParts.length > 3) maximumLength = Integer.parseInt(fieldParts[3]);
            FormField formField = new FormField(name, type, value, maximumLength);
            fields.add(formField);
        }
        form.setFields(fields);
    }

    public String[] GetFundTypes() {
        String sql = "SELECT nazwa_funduszu FROM typ_srodkow_encja_slownikowa";
        try {
            ResultSet rs = Select(sql);
            ArrayList<String> fundTypes = new ArrayList<>();
            while (rs.next()) {
                fundTypes.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            con.close();
            return fundTypes.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] GetFormTypes() {
        String sql = "SELECT nazwa_formularzu FROM typ_formularzu";
        try {
            ResultSet rs = Select(sql);
            ArrayList<String> formTypes = new ArrayList<>();
            while (rs.next()) {
                formTypes.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            con.close();
            return formTypes.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String[] GetActiveFormTypes() {
        //czy_aktywny is a number
        String sql = "SELECT nazwa_formularzu FROM typ_formularzu WHERE czy_aktywny = 1";
        try {
            ResultSet rs = Select(sql);
            ArrayList<String> formTypes = new ArrayList<>();
            while (rs.next()) {
                formTypes.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            con.close();
            return formTypes.toArray(new String[0]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Form GetFormByName(String name){
        String sql = "select * from typ_formularzu WHERE nazwa_formularzu = '" + name + "'";
        try {
            ResultSet rs = Select(sql);
            while (rs.next()) {
                String formName = rs.getString(1);
                int formTypeID = rs.getInt(2);
                ArrayList<FormField> formFields = GetFormFields(formTypeID);
                return new Form(formTypeID, name, "", formFields);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param formTypeID id of form type
     * @return array of FormField objects
     */
    private ArrayList<FormField> GetFormFields(int formTypeID) {
        String sql = "select pf.nazwa_pola, tpf.typ_danej,  tpf.maksymalna_dlugosc from pola_formularzu pf join typ_pol_formularzu tpf on" +
                " pf.typ_pol_formularzu_id_typu = tpf.id_typu where pf.typ_formularzu_id_formularzu = " + formTypeID;
        try {
            ResultSet rs = Select(sql);
            ArrayList<FormField> formFields = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString(1);
                String type = rs.getString(2);
                int maxLength = rs.getInt(3);
                String value = "";
                formFields.add(new FormField(name, type, value, maxLength));
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
     * @param form form template to be added to database
     * @return message
     */
    public String AddForm(Form form) {
        String fieldNames = "";
        String fieldTypes = "";
        String fieldMaxLengths = "";
        for (FormField field : form.getFields()) {
            fieldNames += field.getName() + ",";
            fieldTypes += field.getType() + ",";
            fieldMaxLengths += field.getMaximumLength() + ",";
        }
        if(form.getFields().size() > 0) {
            fieldNames = fieldNames.substring(0, fieldNames.length() - 1);
            fieldTypes = fieldTypes.substring(0, fieldTypes.length() - 1);
            fieldMaxLengths = fieldMaxLengths.substring(0, fieldMaxLengths.length() - 1);
        }
        String sql = "call DodajFormularz(\n'" + form.getName() + "',\n '" + form.getFundName() + "',\n '" + fieldNames + "',\n '" + fieldTypes + "',\n '" + fieldMaxLengths + "')";
        Procedure(sql);
        return "Successfully added a new form";
    }

    /**
     * @param application application to be added to database
     */
    public void AddApplication(Application application, int userID) {
        AddUserIncome(userID,application.form.fields.get(application.form.fields.size() - 1).value);
        application.form.fields.remove(application.form.fields.size() - 1);
        String sql = "call DodajWniosek(" + application.getForm().getFormTypeID() + ", '" + GetStringFromForm(application.getForm()) + "', " + userID + ")";
        Procedure(sql);
    }

    /**
     * @param applicationID id of application
     * @param status status to be set
     */
    private void SetApplicationStatus(int applicationID, String status){
        String sql = "call UstawStatusWniosku(" + applicationID + ", '" + status + "')";
        Procedure(sql);
    }

    /**
     * @param applicationID id of application
     * @param status status to be set
     * @param reviewerID id of reviewer
     */
    public void SetApplicationStatus(int applicationID, String status, int reviewerID){
        String sql = "call UstawStatusWniosku(" + applicationID + ", '" + status + "', " + reviewerID + ")";
        Procedure(sql);
    }

    /**
     * @param fundName name of fund
     * @return current value of fund
     */
    private int GetCurrentFundValue(int fundName){
        String sql = "select kwota_przyznana, kwota_uzyta, kwota_z_poprzedniego_roku from fundusz where typ_srodkow_encja_slownikowa_nazwa_funduszu = " + fundName;
        try {
            ResultSet rs = Select(sql);
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
    private void WithdrawFunds(int fundName, int value){
        String sql = "call WyplacSrodki(" + fundName + ", " + value + ")";
        Procedure(sql);
    }

    /**
     * @param form form to be encoded
     * @return encoded form
     */
    private String GetStringFromForm(Form form) {
        String encodedFields =  "";
        for (FormField field : form.getFields()) {
            encodedFields += field.getType() + ":" + field.getValue() + ":" + field.getName() + ":" + field.getMaximumLength() + ";";
        }
        encodedFields = encodedFields.substring(0, encodedFields.length() - 1);
        return encodedFields;
    }

    /**
     * @param formTypeID id of form
     * @return name of form
     * @throws SQLException wrong sql query
     */
    private String GetFormName(int formTypeID) throws SQLException {
        String sql = "SELECT nazwa_formularzu FROM typ_formularzu WHERE id_formularzu = " + formTypeID;
        ResultSet rs = Select(sql);
        rs.next();
        return rs.getString(1);
    }



    public void GetUser(User user) {
        String sql = "SELECT * FROM uzytkownicy WHERE login = '" + user.getLogin() + "' AND haslo = '" + user.getPassword() + "'";
        try {
            ResultSet rs = Select(sql);
            if(rs.next()) {
                user.setUserID(rs.getInt(1));
                rs.close();
                user.setPermissionLevel(1);

                // Check for admin
                String sqlCheckAdmin = "SELECT * FROM rozpatrujacy WHERE id_uzytkownika = '" + user.getUserID() + "'";
                try {
                    ResultSet rsCheckAdmin = Select(sqlCheckAdmin);
                    if (rsCheckAdmin.next()) {
                        user.setPermissionLevel(2);
                    }
                    rsCheckAdmin.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            stmt.close();
            con.close();
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
    public void AddNewUser(String login, String password, String name, String surname, String company, String pesel,
                            Date birthDate) {
        this.AddNewUser(login, password, name, surname, company, pesel, birthDate, "");
    }

    /**
     * @param login user login
     * @param password user password
     * @param name user name
     * @param surname user surname
     * @param company user company
     * @param pesel user pesel
     * @param birthDate user birthdate
     * @param accountNumber user account number
     */
    private void AddNewUser(String login, String password, String name, String surname, String company, String pesel,
                            Date birthDate, String accountNumber) {
        String procedure = "call DodajWnioskodawce('" + login + "', '" + password + "', '" + name + "', '" + surname + "', '" +
                company + "', '" + pesel + "', '" + birthDate + "', '" + accountNumber + "')";
        Procedure(procedure);
    }

    /**
     * @param userID user id
     * @param income user income
     */
    private void AddUserIncome(int userID, String income) {
        String procedure = "call DodajDochodWnioskodawcy(" + userID + ", '" + income + "')";
        Procedure(procedure);
    }
}
