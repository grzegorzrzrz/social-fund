package panels;

import lib.BasePanel;
import lib.Settings;
import lombok.Getter;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@Getter
public class GenerateReportPanel extends BasePanel {
    // generates report based on two dropdowns: status and form type and date range
    // dropdowns variables
    public String[] statusDropdownChoices = {"Any", "Oczekujacy", "Odrzucony", "Zaakceptowany"};
    public JComboBox statusDropdown;
    public JComboBox formTypeDropdown;
    // two choose date  boxes
    public JDatePanelImpl endDatePicker;
    public JDatePanelImpl startDatePicker;

    public GenerateReportPanel(String[] formTypes) {
        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);

        statusDropdown = new JComboBox(statusDropdownChoices);
        // add "ALL" option to form type dropdown
        String[] formTypeDropdownChoices = new String[formTypes.length + 1];
        formTypeDropdownChoices[0] = "All";
        for (int i = 0; i < formTypes.length; i++) {
            formTypeDropdownChoices[i + 1] = formTypes[i];
        }
        formTypeDropdown = new JComboBox(formTypeDropdownChoices);
        getUpperPanel().add(statusDropdown);
        getUpperPanel().add(formTypeDropdown);

        // set getUpperPanel layout to 2 smaller rows and one big
        getUpperPanel().setLayout(new GridLayout(3, 1, 10, 10));

        JLabel startDateLabel = new JLabel("Start Date");
        startDateLabel.setHorizontalAlignment(JLabel.CENTER);
        startDateLabel.setVerticalAlignment(JLabel.BOTTOM);
        getUpperPanel().add(startDateLabel);
        JLabel endDateLabel = new JLabel("End Date");
        endDateLabel.setHorizontalAlignment(JLabel.CENTER);
        endDateLabel.setVerticalAlignment(JLabel.BOTTOM);
        getUpperPanel().add(endDateLabel);


        // date pickers
        startDatePicker = new JDatePanelImpl(null);
        endDatePicker = new JDatePanelImpl(null);
        getUpperPanel().add(startDatePicker);
        getUpperPanel().add(endDatePicker);

        // Set the default date to today's date
        startDatePicker.getModel().setSelected(true);
        endDatePicker.getModel().setSelected(true);

        setVisible(true);
    }
    public Date GetStartingDate()
    {
        LocalDate dateUtil = ((GregorianCalendar)startDatePicker.getModel().getValue()).toZonedDateTime().toLocalDate();
        return Date.valueOf(dateUtil);
                //java.sql.Date((java.util.Date)(startDatePicker.getModel().getValue().getTime()));
    }
    public Date GetEndingDate()
    {
        LocalDate dateUtil = ((GregorianCalendar)endDatePicker.getModel().getValue()).toZonedDateTime().toLocalDate();
        return Date.valueOf(dateUtil);
                //new java.sql.Date((java.util.Date)(endDatePicker.getModel().getValue().getTime()));
    }
}
