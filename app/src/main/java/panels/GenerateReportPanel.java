package panels;

import lib.BasePanel;
import net.sourceforge.jdatepicker.JDatePicker;

import javax.swing.*;

public class GenerateReportPanel extends BasePanel {
    // generates report based on two dropdowns: status and form type and date range
    // dropdowns variables
    public String[] statusDropdownChoices = {"Any", "Pending", "Approved", "Rejected"};
    public String[] formTypeDropdownChoices = {"Form 1", "Form 2", "Form 3"};
    public JComboBox statusDropdown;
    public JComboBox formTypeDropdown;
    // two choose date  boxes
    public JDatePicker endDatePicker;
    public JDatePicker startDatePicker;

    public GenerateReportPanel() {
        statusDropdown = new JComboBox(statusDropdownChoices);
        formTypeDropdown = new JComboBox(formTypeDropdownChoices);


    }


}
