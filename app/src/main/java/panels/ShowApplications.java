package panels;

import classes.Application;
import lib.BasePanel;
import lib.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ShowApplications extends BasePanel {
    private JComboBox<String> chooseLibrary;
    private JTable libraryInfo;
    private DefaultTableModel ShowApplicationsModel;
    private JScrollPane scrollPane;
    private JLabel statusInfo;
    public ShowApplications(String username){
        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);

        statusInfo = new JLabel("Status: Waiting for change");
        ShowApplicationsModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        libraryInfo = new JTable(ShowApplicationsModel);

        statusInfo.setPreferredSize(new Dimension(300, 100));
        libraryInfo.setPreferredSize(new Dimension(500, 500));

        Dimension minimumSize = new Dimension(100, 100);
        statusInfo.setMinimumSize(minimumSize);


        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(),BoxLayout.Y_AXIS));
        getUpperPanel().add(libraryInfo);
        ShowApplicationsModel.setColumnCount(3);

        setVisible(true);

        filltable( username);

    }


    public void filltable(String user)
    {
        ShowApplicationsModel.setRowCount(0);



        ArrayList<Application> a= Settings.getInstance().mockDatabase.getAplications(user);

        for (int i=0;i<a.size();i++)
        {

            ShowApplicationsModel.addRow(new Object[] {a.get(i).status, a.get(i).creationDate
            });
        }


    }

}
