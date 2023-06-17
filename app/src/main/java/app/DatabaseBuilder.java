package app;

public class DatabaseBuilder {
    private String DBURL = "localhost:1521";
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
