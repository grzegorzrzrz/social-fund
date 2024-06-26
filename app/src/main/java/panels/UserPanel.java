package panels;

import lib.Settings;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

@Getter
public class UserPanel extends JFrame
{
    private int permissionLevel;
    public JButton login;
    public JButton myApplication;

    private String username;
    private JButton addApplication;
    public UserPanel(int permissionLevel, String username)
    {
        this.permissionLevel = permissionLevel;
        this.username = username;

        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);

        JSplitPane splitPane = new JSplitPane();
        JPanel centralPanel = new JPanel();
        JPanel loginPanel = new JPanel();
        JLabel loginLabel = new JLabel();
        myApplication = new JButton("show my applications");

        login = new JButton("login");


        splitPane.setEnabled(false);
        loginPanel.add(loginLabel);
        loginLabel.setText("Currently logged in as: " + username);
        login.setEnabled(true);
        centralPanel.setLayout(new GridLayout(3, 1,100,100));
        loginPanel.setLayout(new GridLayout(1, 2, 300, 100));
        addApplication = new JButton("New application");
        centralPanel.add(addApplication);
        centralPanel.add(myApplication);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(100);
        splitPane.setTopComponent(loginPanel);
        splitPane.setBottomComponent(centralPanel);
        add(splitPane);
        loginPanel.add(login);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fundusz - użytkownik");
        setVisible(true);
    }
}
