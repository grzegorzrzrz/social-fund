package app;

import classes.*;
import lib.InteractiveJTextField;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class MockDatabase {
    public int[] validateLoginData(String username, char[] password) {
        return new int[] {1,1};
//        return new int[]{permission, userID};
    }


    public void addCopy(int returnSelectedBookId, int returnSelectedLibraryId) {
    }

    public void payPenalty(int userID) {
    }

    public Library getLibraryInfo(String toString) {
        return null;
    }

    public void registerUser(User newUser) {
    }

    public ArrayList<Order> getOrders(int parseInt, String rezerwacja) {
        return null;
    }

    public int getUserID(String userData) {
        return 0;
    }

    public void borrowBook(Integer valueOf) {
    }

    public void returnBook(int orderID, int chosenPenaltyID) {
    }

    public void addBook(Book book) {
    }

    public void initializeData() {
    }

    public ArrayList<Penalty> getPenalties() {
        ArrayList<Penalty> o = null;
        return o;
    }

    public String[] getAuthors() {
        return null;
    }

    public String[] getLibrariesNames() {
        return null;
    }

    public ArrayList<Book> getBooks(String s, String s1, String s2, String s3) {
        ArrayList<Book> a = new ArrayList<Book>();
        a.add(new Book(1, "aga", "aga", 1, "aga", 1,"aga"));
        return a;
    }

    public boolean isPenalty(int userId) {
        return false;
    }

    public ArrayList<String> getGenres() {
        return null;
    }

    public ArrayList<Copy> getAvailableCopies(Integer valueOf) {
        return null;
    }

    public void orderBook(String user, int copyID) {
    }

    public ArrayList<String> getFormNames() {
        return new ArrayList<String>() {
            {
                add("aha");
                add("aga");
            }
        };
    }

    public String disableForm(String formName) {
        return "Successfully removed the form";
    }

    public String CreateNewForm(String formName, Vector<Vector> dataVector) {
        return "Successfully added a new form";
    }
}
