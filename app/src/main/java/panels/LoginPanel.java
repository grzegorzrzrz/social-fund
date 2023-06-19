package panels;
import lib.*;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
@Getter
public class LoginPanel extends BasePanel{
    private InteractiveJPasswordField password;
    private InteractiveJTextField username;

    private JButton registerButton;
    public LoginPanel()
    {
        registerButton = new JButton("register");
        getAcceptButton().setText("login");
        password = new InteractiveJPasswordField("password");
        username = new InteractiveJTextField("username");
        password.setBorder(BorderFactory.createLineBorder(Color.black));
        username.setBorder(BorderFactory.createLineBorder(Color.black));
        getUpperPanel().add(username);
        getUpperPanel().add(password);
        getUpperPanel().setLayout(new GridLayout(1,2, 150, 200));
        getUpperPanel().setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        getBottomPanel().add(registerButton);
        getBottomPanel().setLayout(new GridLayout(1,3,50,50));

        setTitle("Login");
        setVisible(true);
    }
}

