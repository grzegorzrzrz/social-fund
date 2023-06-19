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
    private InteractiveJTextField accountNumber;

    public RegisterUserPanel() {
        personsName = new InteractiveJTextField("Type your name");
        surname = new InteractiveJTextField("Type your surname");
        login = new InteractiveJTextField("Type your account login");
        password = new InteractiveJPasswordField("Type your password");
        company = new InteractiveJTextField("Type the name of your company");
        pesel = new InteractiveJTextField("Type your PESEL number");
        birthDate = new InteractiveJTextField("Type your Birth Date format is yyyy-mm-dd");
        accountNumber = new InteractiveJTextField("Type your bank account number");


        getUpperPanel().setLayout(new GridLayout(4,2,50,50));
        getUpperPanel().add(personsName);getUpperPanel().add(surname); getUpperPanel().add(login); getUpperPanel().add(password);
        getUpperPanel().add(company); getUpperPanel().add(pesel); getUpperPanel().add(birthDate); getUpperPanel().add(accountNumber);
        setVisible(true);
    }
}
