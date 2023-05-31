package app;

import classes.*;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;

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
     * @param condName name of data type in database
     * @param data fragment of searched data
     * @return condition for sql query
     */
    private String addCondition(String condName, String data) {
        return condName + " like '%" + data + "%'";
    }

    /**
     * @param rs result from sql query
     * @return list of books
     * @throws SQLException wrong sql query
     */
    private ArrayList<Book> getBooksFromResult(ResultSet rs) throws SQLException {
        ArrayList<Book> books = new ArrayList<>();
        while (rs.next()) {
            int bookID = rs.getInt(1);
            String title = rs.getString(2);
            String author = rs.getString(8) + ' ' + rs.getString(9);
            int pages = rs.getInt(4);
            String isbn = rs.getString(5);
            int year = rs.getInt(6);
            String genre = rs.getString(7);
            Book book = new Book(bookID, title, author, pages, isbn, year, genre);
            books.add(book);
        }
        return books;
    }

    /**
     * @param rs result from sql query
     * @return list of orders
     * @throws SQLException wrong sql query
     */
    private ArrayList<Order> getOrdersFromResult(ResultSet rs) throws SQLException {
        ArrayList<Order> orders = new ArrayList<>();
        while (rs.next()) {
            int orderID = rs.getInt(1);
            String status = rs.getString(2);
            String dateBorrow = rs.getString(3);
            String dateReturn = rs.getString(4);
            String bookTitle = rs.getString(5);
            Order order = new Order(orderID, status, dateBorrow, dateReturn, bookTitle);
            orders.add(order);
        }
        return orders;
    }

    /**
     * Adds author which not already exists in database
     * @param firstName name of author
     * @param lastName surname of author<p>
     */
    private void addAuthor(String firstName, String lastName) {
        String insert = "INSERT INTO Authors Values(Null, '"
                + firstName + "', '"
                + lastName + "', Null, Null)";
        dml(insert);
    }

    /**
     * @param fullName full name of the author
     * @param canAdd determine can the function add new author if one don't exist
     * @return id of author
     */
    public int checkAuthor(String fullName, Boolean canAdd) {
        int authorID = -1;
        String[] nameParts = fullName.split(" ");
        if (nameParts.length != 2) {
            return authorID;
        }
        String firstName = nameParts[0];
        String lastName = nameParts[1];
        String sql = "select author_id from Authors where name = '" + firstName + "' and surname = '" + lastName + "'";
        try {
            ResultSet rs = select(sql);
            if (rs.next()) {
                authorID = rs.getInt(1);
                rs.close();
                stmt.close();
                con.close();
            } else if (canAdd) {
                addAuthor(firstName, lastName);
                rs = select(sql);
                if (rs.next()) {
                    authorID = rs.getInt(1);
                }
                rs.close();
                stmt.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return authorID;
    }

    /**
     * @param username username to app
     * @param password password to app
     * @return list of permission and user id <p>
     * Permission modes: <p>
     * 0 - not logged <p>
     * 1 - user <p>
     * 2 - admin <p>
     */
    public int[] validateLoginData(String username, char[] password) {
        int permission = 0;
        int userID = 0;
        try {
            ResultSet rs = select("select user_id, password, is_admin from users_data join users using(user_data_id)" +
                    " where login='" + username + "'");
            if (rs.next()) {
                if (String.valueOf(password).equals(rs.getString(2)))
                    if (rs.getInt(3) == 0) {
                        permission = 1;
                        userID = rs.getInt(1);
                    } else
                        permission = 2;
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return new int[]{permission, userID};
    }

    /**
     * Searching for books with given parameters, they can be empty
     * @param title fragment of the title
     * @param author full name of author
     * @param isbn 13 number International Standard Book Number
     * @param genre genre of the book
     * @return list of books
     */
    public ArrayList<Book> getBooks(String title, String author, String isbn, String genre) {
        ArrayList<Book> books = new ArrayList<>();
        String sql = "select distinct b.*, a.name, a.surname " +
                " from BOOKS b join AUTHORS a on (b.AUTHOR_ID=a.AUTHOR_ID) " +
                "where " + addCondition("b.title", title) + " and ";
        if (!author.isEmpty()) {
            int authorID = checkAuthor(author, false);
            sql += "b.author_id = " + authorID + "and ";
        }
        if (!isbn.isEmpty())
        {
            sql += "b.ISBN = "  + isbn + " and ";
        }
        sql += addCondition("b.genre", genre);
        try {
            ResultSet rs = select(sql);
            books = getBooksFromResult(rs);
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return books;
    }

    /**
     * @param book book to be added to database
     */
    public void addBook(Book book) {
        String insert = "INSERT INTO Books Values(Null, '" +
                book.getTitle() + "', " +
                checkAuthor(book.getAuthor(), true) + ", " +
                book.getPages() + ", '" +
                book.getISBN() + "', " +
                book.getYear() + ", '" +
                book.getGenre() + "')";
        dml(insert);
    }

    /**
     * @return list of unique genres from all books
     */
    public ArrayList<String> getGenres() {
        ArrayList<String> genres = new ArrayList<>();
        String sql = "select distinct genre from books";
        try {
            ResultSet rs = select(sql);
            while (rs.next()) {
                String genre = rs.getString(1);
                genres.add(genre);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return genres;
    }

    /**
     * @param userID id of user to be checked
     * @return true if user has penalty, false otherwise
     */
    public Boolean isPenalty(int userID) {
        String sql = "select count(*) from penalties_history where USER_ID =" + userID + " and is_paid = 0";
        try {
            ResultSet rs = select(sql);
            rs.next();
            Boolean penalty = rs.getInt(1) != 0;
            rs.close();
            stmt.close();
            con.close();
            return penalty;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param name full name of the library
     * @return searched library
     */
    public Library getLibraryInfo(String name) {
        ArrayList<String> opening = new ArrayList<>();
        ArrayList<String> closing = new ArrayList<>();
        String sql = "select l.library_id, l.name, l.phone_number, a.street, a.street_num, a.city " +
                "from LIBRARIES l join ADDRESSES a using (address_id) where " + addCondition("l.name", name);
        try {
            ResultSet rs = select(sql);
            rs.next();
            int libID = rs.getInt(1);
            String libName = rs.getString(2);
            int phone = rs.getInt(3);
            String street = rs.getString(4) + ' ' + rs.getInt(5);
            String city = rs.getString(6);
            rs.close();
            sql = "select w.OPENING, w.CLOSING from WORK_TIMES w join LIBRARIES l using (library_id) where library_id = " + libID;
            rs = select(sql);
            while (rs.next()) {
                opening.add(rs.getString(1));
                closing.add(rs.getString(2));
            }
            rs.close();
            stmt.close();
            con.close();
            return new Library(libID, libName, street, city, phone, new WorkTime(opening, closing));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    /**
     * @param userID id of the user
     * @param mode determines which orders are searched:<p>
     *             "" - all<p>
     *             "Rezerwacja" - reserved<p>
     *             "Wypozyczona" - borrowed<p>
     *             "Zwrocona" - returned
     * @return list of all orders of user
     */
    public ArrayList<Order> getOrders(int userID, String mode) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "select o.order_id, oh.status, o.DATE_BORROW, o.DATE_RETURN, b.TITLE " +
                "from ORDERS_HISTORY oh join orders o on(oh.order_id=o.order_id) " +
                "join COPIES c on (c.copy_id=o.copy_id) " +
                "join BOOKS b on (c.book_id=b.book_id) " +
                "where oh.user_id =" + userID;
        if (!mode.isEmpty()) {
                sql += " and oh.status = '" + mode + "'";
        }
        try {
            ResultSet rs = select(sql);
            orders = getOrdersFromResult(rs);
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return orders;
    }

    /**
     * Operation of reserving the book
     * @param user name of the user who reserve book
     * @param copyID id of the copy of the book
     */
    public void orderBook(String user, int copyID) {
        try {
            int userID = getUserID(user);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            String insert = "INSERT INTO Orders Values(Null, "
                    + copyID + ", Null, Null)";
            stmt.executeUpdate(insert);
            ResultSet rs = stmt.executeQuery("select order_id from orders order by order_Id desc fetch next 1 rows only");
            rs.next();
            int orderID = rs.getInt(1);
            rs.close();
            insert = "INSERT INTO Orders_History Values(Null, "
                    + orderID + ", " + userID + ", 'Rezerwacja')";
            stmt.executeUpdate(insert);
            String update = "UPDATE Copies " +
                    "SET is_available =0 " +
                    "WHERE copy_id=" + copyID;
            stmt.executeUpdate(update);
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Operation of borrowing the book
     * @param orderID id of the order
     */
    public void borrowBook(int orderID) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            String update = "UPDATE Orders " +
                    "set date_borrow = SYSDATE " +
                    "where order_id = " + orderID;
            stmt.executeUpdate(update);
            update = "Update Orders_History " +
                    "set status = 'Wypozyczona' " +
                    "where order_id = " + orderID;
            stmt.executeUpdate(update);
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Operation of returning the book
     * @param orderID id of the order
     * @param penaltyID determines if the penalty is imposed<p>
     *                  if 0 none, otherwise corresponding to id
     */
    public void returnBook(int orderID, int penaltyID) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            String update = "UPDATE Orders " +
                    "set date_return = SYSDATE " +
                    "where order_id = " + orderID;
            stmt.executeUpdate(update);
            update = "Update Orders_History " +
                    "set status = 'Zwrocona'" +
                    "where order_id = " + orderID;
            stmt.executeUpdate(update);
            String sql = "select o.copy_id, oh.user_id " +
                    "from ORDERS_HISTORY oh join orders o on (oh.order_id=o.order_id) " +
                    "where oh.order_id = " + orderID;
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int copyID = rs.getInt(1);
            int userID = rs.getInt(2);
            rs.close();
            update = "UPDATE Copies " +
                    "SET is_available =1" +
                    "WHERE copy_id=" + copyID;
            stmt.executeUpdate(update);
            if (penaltyID != 0) {
                String insert = "INSERT INTO Penalties_history Values(Null, "
                        + penaltyID + ", " + userID + ", 0)";
                stmt.executeUpdate(insert);
            }
            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return list of type of penalties in libraries
     */
    public ArrayList<Penalty> getPenalties() {
        ArrayList<Penalty> penalties = new ArrayList<>();
        String sql = "select * from penalties";
        try {
            ResultSet rs = select(sql);
            while (rs.next()) {
                int penaltyID = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                int value = rs.getInt(4);
                Penalty penalty = new Penalty(penaltyID, name, description, value);
                penalties.add(penalty);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return penalties;
    }

    /**
     * Operation to pay penalty
     * @param userID id of the user
     */
    public void payPenalty(int userID) {
        String update = "Update penalties_history " +
                "set is_paid = 1 " +
                "where user_id = " + userID;
        dml(update);
    }

    /**
     * @param bookID searched book
     * @return list of copy of searched book
     * @throws Exception book don't have any available copy
     */
    public ArrayList<Copy> getAvailableCopies(int bookID) throws Exception {
        ArrayList<Copy> copies = new ArrayList<>();
        String sql = "select c.copy_id, l.name from COPIES c join libraries l on (c.library_id=l.library_id)" +
                "where c.IS_AVAILABLE = 1 and c.book_id = " + bookID;
        ResultSet rs = select(sql);
        while (rs.next()) {
            int copy_id = rs.getInt(1);
            String libraryName = rs.getString(2);
            copies.add(new Copy(copy_id, libraryName));
        }
        rs.close();
        stmt.close();
        con.close();
        if (copies.isEmpty())
        {
            throw new Exception("Brak dostępnych kopii.");
        }
        else
        {
            return copies;
        }
    }

    /**
     * Operation of registering a new user
     * @param user new user
     */
    public void registerUser(User user) {
        String check = "select * from users_data where login = '" + user.getLogin() + "'";
        String insert = "Insert into Users_data Values(Null, '" + user.getLogin() +
                "', '" + user.getPassword() + "', 0)";
        String sql = "select user_data_id from users_data order by " +
                "user_data_id desc fetch next 1 rows only";
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(check);
            if (rs.next())
            {
                throw new Exception("Użytkownik o podanej nazwie już istnieje.");
            }
            rs.close();
            stmt.executeUpdate(insert);
            rs = stmt.executeQuery(sql);
            rs.next();
            int userdataID = rs.getInt(1);
            rs.close();
            insert = "Insert into Users Values(Null, " +
                    userdataID + ", '" + user.getName() + "', '"
                    + user.getSurname() + "')";
            stmt.executeUpdate(insert);
            stmt.close();
            con.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates new or replace old database with tables and some data
     */
    public void initializeData() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(DBURL, DBUSERNAME, DBPASSWORD);
            stmt = con.createStatement();
            CallableStatement stmtcall = con.prepareCall("Begin " +
                    "for c in (select table_name from user_tables) loop " +
                    "execute immediate ('drop table '||c.table_name||' cascade constraints'); " +
                    "end loop; " +
                    "End;");
            stmtcall.execute();
            stmtcall.close();

            String script = IOUtils.toString(new FileInputStream("../ddl.sql"), StandardCharsets.UTF_8);
            String[] operations = script.split(";");
            for (String operation: operations)
            {
                if (operation.equals("Commit"))
                {
                    break;
                }
                stmt.execute(operation);
            }
            script = IOUtils.toString(new FileInputStream("../dml.sql"), StandardCharsets.UTF_8);
            operations = script.split(";");
            for (String operation: operations)
            {
                if (operation.equals("Commit"))
                {
                    break;
                }
                stmt.execute(operation);
            }

            stmt.close();
            con.close();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @return list of unique libraries
     */
    public String[] getLibrariesNames()
    {
        ArrayList<String> libraries = new ArrayList<>();
        String sql = "select name from libraries";
        try {
            ResultSet rs = select(sql);
            while (rs.next()) {
                libraries.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return libraries.toArray(new String[0]);
    }

    /**
     * @return list of unique authors
     */
    public String[] getAuthors()
    {
        ArrayList<String> authors = new ArrayList<>();
        String sql = "select name || ' ' || surname from AUTHORS";
        try {
            ResultSet rs = select(sql);
            while (rs.next()) {
                authors.add(rs.getString(1));
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return authors.toArray(new String[0]);
    }

    /**
     * Operation of adding new copy of existing book to the given library
     * @param bookID id of the book
     * @param libraryID id of the library
     */
    public void addCopy(int bookID, int libraryID)
    {
        String insert = "insert into Copies Values(Null, " + libraryID + ", " + bookID + ", 1)";
        dml(insert);
    }

    /**
     * @param username username
     * @return user id
     */
    public int getUserID(String username){
        int userID = -1;
        try {
            ResultSet rs = select("select user_id from users_data join users using(user_data_id)" +
                    " where login='" + username + "'");
            if (rs.next()) {
                        userID = rs.getInt(1);
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return userID;
    }

}
