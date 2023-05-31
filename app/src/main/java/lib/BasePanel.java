package lib;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

@Getter
public abstract class BasePanel extends JFrame {
    private JButton cancelButton;
    private JButton acceptButton;
    private JPanel bottomPanel;
    private JPanel upperPanel;

    private JSplitPane defaultSplitPane;

    public BasePanel(){
        cancelButton = new JButton("Cancel");
        acceptButton = new JButton("Accept");
        bottomPanel = new JPanel();
        upperPanel = new JPanel();
        defaultSplitPane = new JSplitPane();
        defaultSplitPane.setResizeWeight(1);
        defaultSplitPane.setEnabled(false);
        Dimension minimumSize = new Dimension(200, 100);
        bottomPanel.setMinimumSize(minimumSize);
        upperPanel.setMinimumSize(minimumSize);
        bottomPanel.add(cancelButton);
        bottomPanel.add(acceptButton);
        bottomPanel.setLayout(new GridLayout(1,2, 50, 50));
        defaultSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        defaultSplitPane.setDividerSize(50);
        defaultSplitPane.setBottomComponent(bottomPanel);
        defaultSplitPane.setTopComponent(upperPanel);
        add(defaultSplitPane);
        setPreferredSize(Settings.getInstance().SMALL_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().SMALL_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().SMALL_WINDOW_LOCATION_X, Settings.getInstance().SMALL_WINDOW_LOCATION_Y);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JRootPane contentPane = getRootPane();
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        actionMap.put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                acceptButton.doClick();
            }
        });

        pack();
    }
}
