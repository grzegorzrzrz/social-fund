package app;

import classes.Application;
import classes.Book;
import classes.Library;
import classes.User;
import lib.Settings;
import panels.*;

import javax.swing.*;
import java.sql.Date;
import java.util.Scanner;
import java.util.Set;


public class App {
    // permission level 0 - not logged in
    // permission level 1 - logged in as user
    // permission level 2 - logged as employee
    private int permissionLevel = 0;
    private int userID = 0;
    private String username = "";
    public App(){
        Login();
    }

    /**
     * opens the MainPanel and handles the listeners
     */
    public void Run()
    {
        //listeners for panels that are visible on specific permission level
        switch(permissionLevel){
            case 0 ->{Login();}
            case 1 ->{
                UserPanel userPanel = new UserPanel(permissionLevel, username);
                userPanel.login.setText("Login");
                userPanel.myApplication.addActionListener(e -> {
                    showApplicattions();
                    userPanel.dispose();
                });
                userPanel.getAddApplication().addActionListener(e -> {
                    addApplication();
                    userPanel.dispose();
                });
                userPanel.getLogin().addActionListener(e->{userPanel.dispose();Login();});
            }

            case 2 -> { // logged as an admin
                AdminPanel adminPanel = new AdminPanel(permissionLevel, username);
                adminPanel.login.setText("Logout");
                adminPanel.getAddForm().addActionListener(e -> {
                    AddForm();
                    adminPanel.dispose();
                });
                adminPanel.getDeactivateForm().addActionListener(e -> {
                    DeactivateForm();
                    adminPanel.dispose();
                });
                adminPanel.getGenerateReport().addActionListener(e -> {
                    GenerateAnalyticReport();
                    adminPanel.dispose();
                });
            }
        }
    }

    private void addApplication() {
        chooseform chose =new chooseform();
        chose.getCancelButton().addActionListener(e -> disposeSubPanel(chose));
        chose.getAcceptButton().addActionListener(e -> {
            Application application= new Application(5,"denied",new Date(2023,11,8));
            application.form.formTypeID=(Integer.parseInt(chose.getForm_id().getText()));
            chose.dispose();
            fillAplication fill =new fillAplication(application);
            fill.getCancelButton().addActionListener(m ->disposeSubPanel(fill));
            fill.getAcceptButton().addActionListener(m->{
                for (int i=0;i<application.form.getFields().size();i++)
                {
                    application.form.fields.get(i).value=fill.pane.get(i).getLeftComponent().toString();
                }
                disposeSubPanel(fill);
            });

        });

    }


    private void showApplicattions() {
        ShowApplications Showapplications= new ShowApplications(username);

        Showapplications.getCancelButton().addActionListener(
                e ->{ disposeSubPanel(Showapplications);});
        Showapplications.getAcceptButton().addActionListener(e->{
            Application application= new Application(5,"denied",new Date(2023,11,8));//getmockdatebase
            Showapplications.dispose();
            show prewiev= new show(application);//Settings.getInstance().database.getAplication(aplication id)
            prewiev.getCancelButton().addActionListener(
                    m ->{ disposeSubPanel(prewiev);})
            ;
        });


    }
    private void DeactivateForm() {
        DeactivateFormPanel deactivateFormPanel = new DeactivateFormPanel((Settings.getInstance().database.getFormNames()).toArray(new String[0]));
        deactivateFormPanel.getCancelButton().addActionListener(e -> disposeSubPanel(deactivateFormPanel));
        deactivateFormPanel.getAcceptButton().addActionListener(e ->{
            String formName = (String)deactivateFormPanel.getChooseForm().getSelectedItem();
            String message = Settings.getInstance().database.disableForm(formName);
            handleMessagePanel(deactivateFormPanel, message);
        });
    }

    private void AddForm() {
        AddFormPanel addFormPanel = new AddFormPanel();
        addFormPanel.getCancelButton().addActionListener(e-> disposeSubPanel(addFormPanel));
        addFormPanel.getAcceptButton().addActionListener(e ->{
            String message = Settings.getInstance().database.CreateNewForm(addFormPanel.getFormName().getText(), addFormPanel.getFormTableModel().getDataVector());
            handleMessagePanel(addFormPanel, message);
        });
    }

    private void GenerateAnalyticReport() {
       // GenerateReportPanel generateReportPanel = new GenerateReportPanel();

    }




    private void Register(){
        RegisterUserPanel registerPanel = new RegisterUserPanel();
        registerPanel.getCancelButton().addActionListener(e -> disposeSubPanel(registerPanel));
        registerPanel.getAcceptButton().addActionListener(e -> {
            User newUser = new User(registerPanel.getPersonsName().getText(), registerPanel.getSurname().getText(),
                    registerPanel.getLogin().getText(), registerPanel.getPassword().getText());
            try {
                Settings.getInstance().database.registerUser(newUser);
            }
            catch (RuntimeException exc)
            {
                handleMessagePanel(registerPanel, "Could not create a new user account!");
            }
            disposeSubPanel(registerPanel);
        });
    }



    /**
     *  gets information from database if login was successful.
     */
    private void Login() {
        if (permissionLevel == 0) {
            LoginPanel loginPanel = new LoginPanel();
            loginPanel.getAcceptButton().addActionListener(e -> {
                int [] data = Settings.getInstance().database.validateLoginData(loginPanel.getUsername().getText(), loginPanel.getPassword().getPassword());
                switch (data[0])
                {
                    case 0 -> {// show window couldn't log in
                        permissionLevel = 0;
                        handleMessagePanel(loginPanel, "Login failed: invalid data");
                    }
                    case 1 -> {
                        username = loginPanel.getUsername().getText();
                        userID = data[1];
                        permissionLevel = 1;
                        disposeSubPanel(loginPanel);

                    }
                    case 2 -> {
                        username = loginPanel.getUsername().getText();
                        permissionLevel = 2;
                        disposeSubPanel(loginPanel);
                    }
                }
            });
            loginPanel.getCancelButton().addActionListener(e -> disposeSubPanel(loginPanel));
        }
        else {
            permissionLevel=0;
            userID=0;
            Run();
        }
    }

    /**
     * @param frameToDispose can only be used if you want to open MainPanel after closing your current one.
     */
    private void disposeSubPanel(JFrame frameToDispose)
    {
        frameToDispose.dispose();
        Run();
    }


    /**
     * @param args can take one of the following arguments:<p>
     *             res - Creates or replace old database with tables and data<p>
     *             con - Takes 3 additional arguments with information about url, username and password to change database to which app connects to
     */
    public static void main(String[] args){
//        if(args.length > 0)
//        {
//            if (args[0].equals("res"))
//            {
//                Settings.getInstance().database = new DatabaseBuilder().build();
//                Settings.getInstance().database.initializeData();
//            }
//            else if (args[0].equals("con"))
//            {
//                Settings.getInstance().database = new DatabaseBuilder().setDBURL(args[1]).setDBUSERNAME(args[2]).setDBPASSWORD(args[3]).build();
//                Settings.getInstance().database.initializeData();
//            }
//        }
//
//        else {
//            Settings.getInstance().database = new DatabaseBuilder().build();
//        }
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e){
            // will use the default "Metal" Look and Feel instead
        }
        Settings.getInstance().database = new MockDatabase();
        new App();
    }
    private void handleMessagePanel(JFrame callingPanel, String textToShow)
    {
        callingPanel.setEnabled(false);
        MessagePanel notNumber = new MessagePanel(textToShow);
        notNumber.getAcceptButton().addActionListener(f -> {
            notNumber.dispose();
            callingPanel.setEnabled(true);});
    }

}