package app;

public class DatabaseBuilder {
    //connect to enterprise:21.3
    private String DBURL = "jdbc:oracle:thin:@localhost:1521/ORCLCDB";
    private String DBUSERNAME = "c##develop";
    private String DBPASSWORD =  "oracle";

    public DatabaseBuilder setDBURL(String dburl) {
        DBURL = dburl;
        return this;
    }

    public DatabaseBuilder setDBUSERNAME(String dbusername) {
        DBUSERNAME = dbusername;
        return this;
    }

    public DatabaseBuilder setDBPASSWORD(String dbpassword) {
        DBPASSWORD = dbpassword;
        return this;
    }

    public Database build() {
        return new Database(DBURL, DBUSERNAME, DBPASSWORD);
    }

}
