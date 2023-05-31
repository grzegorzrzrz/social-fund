package panels;

import lib.BasePanel;
import lib.Settings;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends BasePanel {
    public MessagePanel(String message){
        setPreferredSize(Settings.getInstance().MESSAGE_WINDOW_SIZE);
        setMinimumSize(Settings.getInstance().MESSAGE_WINDOW_SIZE);
        setLocation(Settings.getInstance().MESSAGE_WINDOW_LOCATION_X, Settings.getInstance().MESSAGE_WINDOW_LOCATION_Y);
        pack();
        getBottomPanel().remove(getCancelButton());
        JLabel messageField = new JLabel();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        messageField.setBorder(BorderFactory.createEmptyBorder(50,0,0,0));
        messageField.setFont(new Font("SansSerif", Font.ITALIC, 20));
        messageField.setText(message);
        getUpperPanel().add(messageField);
        setVisible(true);
    }
}
