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
    public RegisterUserPanel() {
        personsName = new InteractiveJTextField("Type your name");
        surname = new InteractiveJTextField("Type your surname");
        login = new InteractiveJTextField("Type your account login");
        password = new InteractiveJPasswordField("Type your password");
        getUpperPanel().setLayout(new GridLayout(2,2,100,100));
        getUpperPanel().add(personsName);getUpperPanel().add(surname); getUpperPanel().add(login); getUpperPanel().add(password);
        setVisible(true);
    }
}
