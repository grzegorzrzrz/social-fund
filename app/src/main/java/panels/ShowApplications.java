package panels;

import classes.User;
import lib.BasePanel;
import lib.Settings;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.Vector;

public class ShowApplications extends BasePanel {
    public JTable applicationstable;
    private DefaultTableModel ShowApplicationsModel;
    public ShowApplications(User user){
        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);


        ShowApplicationsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        applicationstable = new JTable(ShowApplicationsModel);

        applicationstable.setPreferredSize(new Dimension(500, 500));

        Dimension minimumSize = new Dimension(100, 100);



        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(),BoxLayout.Y_AXIS));
        getUpperPanel().add(applicationstable);
        ShowApplicationsModel.setColumnCount(2);

        setVisible(true);

        filltable( user);

    }


    public void filltable(User user)
    {
        ShowApplicationsModel.setRowCount(0);



        Vector<Pair<Integer, Date>> a= Settings.getInstance().database.GetApplicationsByUser(user);

        for (int i=0;i<a.size();i++)
        {

            ShowApplicationsModel.addRow(new Object[] {a.get(i).getLeft(), a.get(i).getRight()
            });
        }


    }

}
