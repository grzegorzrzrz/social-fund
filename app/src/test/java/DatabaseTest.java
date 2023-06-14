//import app.Database;
//import app.DatabaseBuilder;
//import classes.Book;
//import classes.Library;
//import classes.User;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
////Testy mogą nie przechodzić gdy nie zostanie uzyskane połączenie z bazą danych z powodu zbyt długiej próby połączenia
//public class DatabaseTest {
//    Database mockDatabase = new DatabaseBuilder()
//            .setDBURL("jdbc:oracle:thin:@//ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl")
//            .setDBUSERNAME("z95")
//            .setDBPASSWORD("36aydi")
//            .build();
//    @BeforeEach
//    void reset()
//    {
//        mockDatabase.initializeData();
//    }
//    @Test
//    void checkAuthor()
//    {
//        Assertions.assertEquals(1,
//                mockDatabase.checkAuthor("Adam Mickiewicz", false));
//    }
//    @Test
//    void checkAuthorNotInDatabase()
//    {
//        Assertions.assertEquals(-1,
//                mockDatabase.checkAuthor("Jan Parandowski", false));
//    }
//    @Test
//    void validateLoginUser()
//    {
//        int[] data = mockDatabase.validateLoginData("user1", new char[]{'p', 'a', 's', 's', 'w', 'o', 'r', 'd', '1'});
//        Assertions.assertEquals(1, data[0]);
//        Assertions.assertEquals(1, data[1]);
//    }
//    @Test
//    void validateLoginAdmin()
//    {
//        int[] data = mockDatabase.validateLoginData("admin1", new char[]{'a', 'd', 'm', 'i', 'n', '1'});
//        Assertions.assertEquals(2, data[0]);
//        Assertions.assertEquals(0, data[1]);
//    }
//    @Test
//    void validateLoginIncorrect()
//    {
//        int[] data = mockDatabase.validateLoginData("admin1", new char[]{'h', 'a', 's', 'l', 'o'});
//        Assertions.assertEquals(0, data[0]);
//        Assertions.assertEquals(0, data[1]);
//    }
//    @Test
//    void getBooksAll()
//    {
//        Assertions.assertEquals(5, mockDatabase.getBooks("","","","").size());
//    }
//    @Test
//    void getBooksTitle()
//    {
//        Book first = mockDatabase.getBooks("Tadeusz","","","").get(0);
//        Assertions.assertEquals("Pan Tadeusz", first.getTitle());
//        Assertions.assertEquals("Adam Mickiewicz", first.getAuthor());
//        Assertions.assertEquals(400, first.getPages());
//        Assertions.assertEquals("1234567890123", first.getISBN());
//        Assertions.assertEquals(1834, first.getYear());
//        Assertions.assertEquals("poemat", first.getGenre());
//    }
//    @Test
//    void getBooksAuthor()
//    {
//        Assertions.assertEquals(2, mockDatabase.getBooks("","Adam Mickiewicz","","").size());
//    }
//    @Test
//    void getBooksISBN()
//    {
//        Book book = mockDatabase.getBooks("","","3456789012345","").get(0);
//        Assertions.assertEquals("Lalka", book.getTitle());
//        Assertions.assertEquals("Boleslaw Prus", book.getAuthor());
//        Assertions.assertEquals(600, book.getPages());
//        Assertions.assertEquals(1890, book.getYear());
//        Assertions.assertEquals("powiesc", book.getGenre());
//    }
//    @Test
//    void getBooksIncorrect()
//    {
//        Assertions.assertEquals(0, mockDatabase.getBooks("Potop","","","").size());
//    }
//    @Test
//    void addBook()
//    {
//        Book newBook = new Book(0, "test", "Adam Mickiewicz", 5, "1111111111111", 2023, "test");
//        Assertions.assertEquals(2, mockDatabase.getBooks("", "Adam Mickiewicz", "", "").size());
//        mockDatabase.addBook(newBook);
//        Assertions.assertEquals(3, mockDatabase.getBooks("", "Adam Mickiewicz", "", "").size());
//
//    }
//    @Test
//    void getLibraryInfo()
//    {
//        Library lib = mockDatabase.getLibraryInfo("Biblioteka Miejska");
//        Assertions.assertEquals(1, lib.getLibraryID());
//        Assertions.assertEquals("Biblioteka Miejska", lib.getName());
//        Assertions.assertEquals("Piotrkowska 100", lib.getStreet());
//        Assertions.assertEquals("Lodz", lib.getCity());
//        Assertions.assertEquals(123456789, lib.getPhone());
//        Assertions.assertEquals(5, lib.getWorkTimes().getOpening().size());
//    }
//    @Test
//    void getOrdersAll()
//    {
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"").size());
//        mockDatabase.orderBook("user1",1);
//        Assertions.assertEquals("Rezerwacja", mockDatabase.getOrders(1,"").get(0).getStatus());
//        mockDatabase.borrowBook(1);
//        Assertions.assertEquals("Wypozyczona", mockDatabase.getOrders(1,"").get(0).getStatus());
//        mockDatabase.returnBook(1,0);
//        Assertions.assertEquals("Zwrocona", mockDatabase.getOrders(1,"").get(0).getStatus());
//    }
//    @Test
//    void getOrdersReserved()
//    {
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Rezerwacja").size());
//        mockDatabase.orderBook("user1",1);
//        Assertions.assertEquals(1, mockDatabase.getOrders(1,"Rezerwacja").size());
//        mockDatabase.borrowBook(1);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Rezerwacja").size());
//        mockDatabase.returnBook(1,0);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Rezerwacja").size());
//    }
//    @Test
//    void getOrdersBorrowed()
//    {
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Wypozyczona").size());
//        mockDatabase.orderBook("user1",1);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Wypozyczona").size());
//        mockDatabase.borrowBook(1);
//        Assertions.assertEquals(1, mockDatabase.getOrders(1,"Wypozyczona").size());
//        mockDatabase.returnBook(1,0);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Wypozyczona").size());
//    }
//    @Test
//    void getOrdersReturned()
//    {
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Zwrocona").size());
//        mockDatabase.orderBook("user1",1);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Zwrocona").size());
//        mockDatabase.borrowBook(1);
//        Assertions.assertEquals(0, mockDatabase.getOrders(1,"Zwrocona").size());
//        mockDatabase.returnBook(1,0);
//        Assertions.assertEquals(1, mockDatabase.getOrders(1,"Zwrocona").size());
//    }
//    @Test
//    void getPenalties()
//    {
//        Assertions.assertEquals("Opoznienie w zwrocie", mockDatabase.getPenalties().get(0).getName());
//        Assertions.assertEquals("Opoznienie w zwrocie ksiazki", mockDatabase.getPenalties().get(0).getDescription());
//        Assertions.assertEquals(5, mockDatabase.getPenalties().get(0).getValue());
//    }
//    @Test
//    void addAndPayPenalty()
//    {
//        mockDatabase.orderBook("user1",1);
//        mockDatabase.borrowBook(1);
//        mockDatabase.returnBook(1,3);
//        Assertions.assertEquals(true, mockDatabase.isPenalty(1));
//        mockDatabase.payPenalty(1);
//        Assertions.assertEquals(false, mockDatabase.isPenalty(1));
//    }
//    @Test
//    void registerUser()
//    {
//        User newUser = new User("Ewa", "Markowska", "nowy", "nowy");
//        mockDatabase.registerUser(newUser);
//        int[] validation = mockDatabase.validateLoginData("nowy", new char[]{'n', 'o', 'w','y'});
//        Assertions.assertEquals(1, validation[0]);
//    }
//    @Test
//    void getLibrariesNames()
//    {
//        String[] result = mockDatabase.getLibrariesNames();
//        Assertions.assertEquals("Biblioteka Miejska", result[0]);
//        Assertions.assertEquals("Biblioteka Uniwersytecka", result[2]);
//        Assertions.assertEquals("Biblioteka Szkolna", result[4]);
//    }
//    @Test
//    void getAvailableCopies()
//    {
//        try {
//            Assertions.assertEquals(5, mockDatabase.getAvailableCopies(1).size());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//    @Test
//    void addCopy()
//    {
//        mockDatabase.addCopy(1,1);
//        try {
//            Assertions.assertEquals(6, mockDatabase.getAvailableCopies(1).size());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    @Test
//    void getUserID()
//    {
//        Assertions.assertEquals(1, mockDatabase.getUserID("user1"));
//    }
//}
