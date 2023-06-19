package panels;

import classes.Application;
import lib.BasePanel;
import lib.InteractiveJTextField;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProcessAnApplication extends BasePanel {
    public JTable applicationTable;

    private InteractiveJTextField formName;
    private InteractiveJTextField fundName;

    private DefaultTableModel formTableModel;
    public ProcessAnApplication(Application application){

        formTableModel = new DefaultTableModel();

        JPanel upperPanel = new JPanel();
        applicationTable = new JTable(formTableModel);
        applicationTable.getTableHeader().setReorderingAllowed(false);

        formTableModel.addColumn("name");
        formTableModel.addColumn("type");

        formTableModel.addRow(new Object[]{null,null});
        JScrollPane scrollPane = new JScrollPane(applicationTable);
        formName = new InteractiveJTextField("Type the name of the new form");
        fundName = new InteractiveJTextField("Type the name of the fund");
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setEnabled(false);
        upperPanel.add(formName);
        upperPanel.add(fundName);
        upperPanel.setLayout(new GridLayout(2,1,10,10));
        splitPane.setLeftComponent(upperPanel);
        splitPane.setRightComponent(scrollPane);
        getUpperPanel().add(splitPane);
        setVisible(true);
    }

}
