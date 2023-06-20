package app;

import classes.*;
import lib.InteractiveJTextField;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class MockDatabase {
    public int[] validateLoginData(String username, char[] password) {
        return new int[] {2,1};
//        return new int[]{permission, userID};
    }





    public void registerUser(User newUser) {
    }



    public int getUserID(String userData) {
        return 0;
    }


    public void initializeData() {
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

    public String CreateNewForm(Form form) {
        return "Successfully added a new form";
    }

    public ArrayList<Application> getAplications(String user) {
        ArrayList<Application> odp= new ArrayList<Application>();
        Application application= new Application(new Applicant(), "denied",new Date(123,11,8), new Form());
        odp.add(application);
        return odp;
    }
}
