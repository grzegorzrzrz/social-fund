package app;

import classes.*;
import lib.InteractiveJTextField;
import lib.Settings;
import org.apache.commons.lang3.tuple.Pair;
import panels.*;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import java.util.Vector;


public class App {
    private User user;

    public App(){
        user = new User();
        Login();
    }

    /**
     * opens the MainPanel and handles the listeners
     */
    public void Run()
    {
        //listeners for panels that are visible on specific permission level
        switch(user.getPermissionLevel()){
            case 0 ->{Login();}
            case 1 ->{
                UserPanel userPanel = new UserPanel(user.getPermissionLevel(), user.getLogin());
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
                AdminPanel adminPanel = new AdminPanel(user.getPermissionLevel(), user.getLogin());
                adminPanel.login.setText("Logout");
                adminPanel.login.addActionListener(e -> {
                    adminPanel.dispose();
                    Login();
                });
                adminPanel.chooseApplicationToView.addActionListener(e ->{
                    ChooseAnApplicationToView();
                    adminPanel.dispose();
                });
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

    private void ChooseAnApplicationToView() {
        Vector<Pair<Integer, Date>> pendingApplications = Settings.getInstance().database.GetPendingApplications();
        ChooseApplicationToView chooseApplicationToView = new ChooseApplicationToView(pendingApplications);
        chooseApplicationToView.getCancelButton().addActionListener(e -> disposeSubPanel(chooseApplicationToView));
        chooseApplicationToView.getAcceptButton().addActionListener(e ->{
        Integer chosenID = (Integer) chooseApplicationToView.getApplicationsBox().getSelectedItem();
        chooseApplicationToView.dispose();
        DoProcessAnApplication(chosenID);
        });
    }

    private void DoProcessAnApplication(Integer chosenID) {
        Application selectedApplication = Settings.getInstance().database.GetApplicationInfo(chosenID);
        ProcessAnApplication processAnApplication = new ProcessAnApplication(selectedApplication);

        processAnApplication.getCancelButton().addActionListener(e -> {
            processAnApplication.dispose();
            ChooseAnApplicationToView();
        });
        processAnApplication.getAcceptButton().addActionListener(e ->{

        });
    }

    private void addApplication() {
        chooseform chose =new chooseform();
        chose.getCancelButton().addActionListener(e -> disposeSubPanel(chose));
        chose.getAcceptButton().addActionListener(e -> {
            String formid= chose.getForm_id().getItemAt(chose.getForm_id().getSelectedIndex());
            Application application= new Application();
            application.form=Settings.getInstance().database.GetFormByName(formid);
            java.util.Vector pom=new java.util.Vector();
            pom.add("dochód na członka rodziny");
            pom.add("Int");
            pom.add("0");
            pom.add("30");
            application.getForm().getFields().add(new FormField(pom));
            chose.dispose();
            fillAplication fill =new fillAplication(application);
            fill.getCancelButton().addActionListener(m ->disposeSubPanel(fill));
            fill.getAcceptButton().addActionListener(m->{
                for (int i=0;i<application.form.getFields().size();i++)
                {
                    application.form.fields.get(i).value= ((InteractiveJTextField)fill.pane.get(i).getRightComponent()).getText();
                }
                Settings.getInstance().database.AddApplication(application,user.getUserID());
                handleMessagePanel(fill, "successful add application");



            });

       });

    }


    private void showApplicattions() {
        ShowApplications Showapplications= new ShowApplications(user);

        Showapplications.getCancelButton().addActionListener(
                e ->{ disposeSubPanel(Showapplications);});
        Showapplications.getAcceptButton().addActionListener(e->{

            Application application= Settings.getInstance().database.GetApplicationInfo((Integer) Showapplications.applicationstable.getValueAt(Showapplications.applicationstable.getSelectedRow(),0));
            Showapplications.dispose();
            show prewiev= new show(application);
            prewiev.getCancelButton().addActionListener(
                    m ->{ disposeSubPanel(prewiev);});
        });


    }
    private void DeactivateForm() {
        DeactivateFormPanel deactivateFormPanel = new DeactivateFormPanel((Settings.getInstance().database.GetActiveFormTypes()));
        deactivateFormPanel.getCancelButton().addActionListener(e -> disposeSubPanel(deactivateFormPanel));
        deactivateFormPanel.getAcceptButton().addActionListener(e ->{
            String formType = (String)deactivateFormPanel.getChooseForm().getSelectedItem();
            String message = Settings.getInstance().database.DisableForm(formType);
            handleMessagePanel(deactivateFormPanel, message);
        });
    }

    private void AddForm() {
        AddFormPanel addFormPanel = new AddFormPanel(Settings.getInstance().database.GetFundTypes());
        addFormPanel.getCancelButton().addActionListener(e-> disposeSubPanel(addFormPanel));
        addFormPanel.getAcceptButton().addActionListener(e ->{
            ArrayList<FormField> fields = new ArrayList<>();
            for (var vector:
                    addFormPanel.getFormTableModel().getDataVector()) {
                // check if any value is null
                Boolean add = true;
                for (var value:
                        vector) {
                    if (value == null) {
                        add = false;
                    }
                }
                if(add) fields.add(new FormField(vector));
            }

            Form newForm = new Form(0, addFormPanel.getFormName().getText(),
                    addFormPanel.getFundComboBox().getSelectedItem().toString(), fields);
            String message = Settings.getInstance().database.AddForm(newForm);
            handleMessagePanel(addFormPanel, message);
        });
    }

    private void GenerateAnalyticReport() {
        GenerateReportPanel generateReportPanel = new GenerateReportPanel(Settings.getInstance().database.GetFormTypes());

        generateReportPanel.getCancelButton().addActionListener(e -> disposeSubPanel(generateReportPanel));
        generateReportPanel.getAcceptButton().addActionListener(e -> {
            StringBuilder message = Settings.getInstance().database.GenerateReport(generateReportPanel.GetStartingDate(), generateReportPanel.GetEndingDate(),
                    generateReportPanel.statusDropdown.getSelectedItem().toString(), generateReportPanel.formTypeDropdown.getSelectedItem().toString());
            // save to csv
            PrintWriter pw = null;
            try {
                pw = new PrintWriter(new File("export.csv"));
                pw.write(message.toString());
                pw.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            handleMessagePanel(generateReportPanel, "Saved to: export.csv");
        });


    }

    private void Register(){
        RegisterUserPanel registerPanel = new RegisterUserPanel();
        registerPanel.getCancelButton().addActionListener(e -> {
            registerPanel.dispose();
            Login();
        });

        registerPanel.getAcceptButton().addActionListener(e -> {

            try {
                String tempDate = registerPanel.getBirthDate().getText();
                String[] splitDate = tempDate.split("-");

                Date birthDate = new Date(Integer.parseInt(splitDate[0]),
                        Integer.parseInt(splitDate[1]),
                        Integer.parseInt(splitDate[2]));

                Settings.getInstance().database.AddNewUser(registerPanel.getLogin().getText(),
                        registerPanel.getPassword().getText(), registerPanel.getPersonsName().getText(),
                        registerPanel.getSurname().getText(), registerPanel.getCompany().getText(),
                        registerPanel.getPesel().getText(), birthDate);

                handleMessagePanel(registerPanel, "Successfully created a new account");
            }
            catch (NumberFormatException ex){
                handleMessagePanel(registerPanel, "ERROR, incorrect data in Birth Date field");
            }
            catch (RuntimeException exc)
            {
                handleMessagePanel(registerPanel, "Could not create a new user account!");
            }
        });
    }



    /**
     *  gets information from database if login was successful.
     */
    private void Login() {
       if (user.getPermissionLevel() == 0) {
            LoginPanel loginPanel = new LoginPanel();
            loginPanel.getAcceptButton().addActionListener(e -> {
                user.setLogin(loginPanel.getUsername().getText());
                user.setPassword(loginPanel.getPassword().getText());
                Settings.getInstance().database.GetUser(user);
                if(user.getPermissionLevel() == 0)
                    handleMessagePanel(loginPanel, "Login failed: invalid data");
                else
                    disposeSubPanel(loginPanel);
            });
            loginPanel.getCancelButton().addActionListener(e -> disposeSubPanel(loginPanel));
            loginPanel.getRegisterButton().addActionListener( e-> {
                loginPanel.dispose();
                Register();
            });
        }
        else {
            user.setPermissionLevel(0);
            user.setUserID(0);
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
        try {

            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception e){
            // will use the default "Metal" Look and Feel instead
        }
        Settings.getInstance().database = new DatabaseBuilder().build();
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