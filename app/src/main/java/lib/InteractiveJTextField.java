package lib;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InteractiveJTextField extends JTextField {
    private boolean clearField;
    public InteractiveJTextField(String defaultMessage) {
        super(defaultMessage);
        this.clearField = true;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(clearField) {
                    setText("");
                    clearField = false;
                }
                super.keyPressed(e);
            }
            });
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if(getText().equals("")) {
                    setText(defaultMessage);
                    clearField = true;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(getText().equals("")) {
                    setText(defaultMessage);
                    clearField = true;
                }
            }
        });
    }

}
