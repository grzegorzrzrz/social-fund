package panels;
import  lib.*;
import lombok.Getter;

import java.awt.*;

@Getter
public class RegisterUserPanel extends BasePanel {
    private InteractiveJTextField personsName;
    private InteractiveJTextField surname;
    private InteractiveJTextField login;
    private InteractiveJPasswordField password;
    private InteractiveJTextField company;
    private InteractiveJTextField pesel;
    private InteractiveJTextField birthDate;

    public RegisterUserPanel() {
        personsName = new InteractiveJTextField("Name");
        surname = new InteractiveJTextField("Surname");
        login = new InteractiveJTextField("Account login");
        password = new InteractiveJPasswordField("Password");
        company = new InteractiveJTextField("Name of your company");
        pesel = new InteractiveJTextField("PESEL number");
        birthDate = new InteractiveJTextField("Birth Date format is yyyy-mm-dd");


        getUpperPanel().setLayout(new GridLayout(4,2,50,50));
        getUpperPanel().add(personsName);getUpperPanel().add(surname); getUpperPanel().add(login); getUpperPanel().add(password);
        getUpperPanel().add(company); getUpperPanel().add(pesel); getUpperPanel().add(birthDate);
        setTitle("Register");
        setVisible(true);
    }
}
