package panels;

import lib.Settings;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.text.MessageFormat;

@Getter
public class AdminPanel extends JFrame
{
    private int permissionLevel;
    public JButton login;
    public JButton addForm;
    public JButton deactivateForm;
    public JButton generateReport;
    private String username;
    private JButton registerButton;
    public AdminPanel(int permissionLevel, String username)
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
        registerButton = new JButton("Register");

        login = new JButton("login");

        addForm = new JButton("Add a new form for users");
        deactivateForm = new JButton("Deactivate a form");
        generateReport = new JButton("Generate an analytics report");

        splitPane.setEnabled(false);
        loginPanel.add(loginLabel);

        centralPanel.setLayout(new GridLayout(3, 1, 100, 100));
        centralPanel.add(addForm); centralPanel.add(deactivateForm); centralPanel.add(generateReport);

        loginPanel.add(login);

        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(100);
        splitPane.setTopComponent(loginPanel);
        splitPane.setBottomComponent(centralPanel);
        add(splitPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Fundusz - admin");
        setVisible(true);
    }
}
