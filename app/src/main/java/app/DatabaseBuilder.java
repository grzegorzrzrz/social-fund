package app;

public class DatabaseBuilder {
    private String DBURL = "localhost:" + System.getEnv('DB_PORT');
    private String DBUSERNAME = System.getEnv('DB_USERNAME');
    private String DBPASSWORD = System.getEnv('DB_PASSWD');

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
