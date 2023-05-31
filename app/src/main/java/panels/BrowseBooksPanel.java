package panels;

import lib.BasePanel;

import javax.swing.*;

public class BrowseBooksPanel extends BasePanel {
    public JButton reserveBook;
    public BrowseBooksPanel(int PermisionLevel) {
        reserveBook = new JButton("reserve Books");
        getUpperPanel().add(reserveBook);

        if (PermisionLevel==1) {
            reserveBook.setVisible(true);
        }
        else
        {
            reserveBook.setVisible(false);
        }
        setVisible(true);
    }


}
