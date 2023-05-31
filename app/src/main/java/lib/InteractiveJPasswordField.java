package lib;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InteractiveJPasswordField extends JPasswordField {
    private boolean clearField;

    public InteractiveJPasswordField(String defaultMessage) {
        super(defaultMessage);
        clearField = true;
        setEchoChar('\u0000');
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(clearField){
                    setText("");
                    clearField = false;
                    setEchoChar('*');
                }
                super.keyPressed(e);}
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
                if(getPassword().length == 0) {
                    setText(defaultMessage);
                    clearField = true;
                    setEchoChar('\u0000');
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(getPassword().length == 0) {
                    setText(defaultMessage);
                    clearField = true;
                    setEchoChar('\u0000');
                }
            }
        });
    }

}
