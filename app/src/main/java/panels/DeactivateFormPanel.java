package panels;

import lib.BasePanel;
import lombok.Getter;

import javax.swing.*;

@Getter
public class DeactivateFormPanel extends BasePanel {
    JComboBox chooseForm;
    public DeactivateFormPanel(String[] formData)
    {
        chooseForm = new JComboBox<>(formData);
        getUpperPanel().add(chooseForm);

        setVisible(true);

    }
}
