package panels;

import classes.Application;
import lib.BasePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProcessAnApplication extends BasePanel {
    public final JTable applicationTable;
    private final JLabel formName;
    private final JLabel fundName;

    private final DefaultTableModel applicationTableModel;
    private final DefaultTableModel applicantTableModel;
    public ProcessAnApplication(Application application){

        applicantTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        applicationTableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JPanel upperPanel = new JPanel();


        JTable applicantTable = new JTable(applicantTableModel);
        applicantTable.getTableHeader().setReorderingAllowed(false);

        applicationTable = new JTable(applicationTableModel);
        applicationTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane applicationScrollPane = new JScrollPane(applicationTable);
        JScrollPane applicantScrollPane = new JScrollPane(applicantTable);

        formName = new JLabel();
        fundName = new JLabel();
        JSplitPane mainSplitPane = new JSplitPane();
        JSplitPane upperSplitPane = new JSplitPane();
        mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        upperSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        mainSplitPane.setEnabled(false);
        upperSplitPane.setEnabled(false);

        upperPanel.add(formName);
        upperPanel.add(fundName);
        upperPanel.setLayout(new GridLayout(2,1,10,10));

        applicantScrollPane.setPreferredSize(new Dimension(100, 100));
        upperSplitPane.setLeftComponent(applicantScrollPane);
        upperSplitPane.setRightComponent(upperPanel);
        mainSplitPane.setLeftComponent(upperSplitPane);
        mainSplitPane.setRightComponent(applicationScrollPane);
        getUpperPanel().add(mainSplitPane);
        FillFieldsWithData(application);


        applicantScrollPane.getColumnHeader().setVisible(false);
        setVisible(true);
    }
    private void FillFieldsWithData(Application application)
    {
        applicantTableModel.addColumn("name");
        applicantTableModel.addColumn("type");

        applicantTableModel.addRow(new Object[]{"Status", application.getStatus()});
        applicantTableModel.addRow(new Object[]{"Creation Date", application.getCreationDate()});
        applicantTableModel.addRow(new Object[]{"Applicant Data:", null});

        applicantTableModel.addRow(new Object[]{"Company", application.applicant.getCompany()});
        applicantTableModel.addRow(new Object[]{"PESEL", application.applicant.getPesel()});
        applicantTableModel.addRow(new Object[]{"Birth Date", application.applicant.getBirthDate()});
        applicantTableModel.addRow(new Object[]{"Account Number", application.applicant.getAccountNumber()});
        applicantTableModel.addRow(new Object[]{"Earnings", application.applicant.getEarnings()});
        formName.setText(application.form.name);
        fundName.setText(application.form.fundName);

        applicationTableModel.addColumn("name");
        applicationTableModel.addColumn("type");
        for (var field:
             application.form.fields) {
            applicationTableModel.addRow(new Object[]{field.getValue(), field.getValue()});
        }

    }
}
