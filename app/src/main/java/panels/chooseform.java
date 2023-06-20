package panels;

import lib.BasePanel;
import lib.InteractiveJPasswordField;
import lib.InteractiveJTextField;
import lib.Settings;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
@Getter

public class chooseform extends BasePanel {
        private JComboBox<String> form_id;
    private JTextArea form;
        public chooseform()
        {
            form = new JTextArea("which form would you like to fullfill?:");
            form.setEditable(false);
            form_id = new JComboBox<String>(Settings.getInstance().database.GetActiveFormTypes());
            JSplitPane upperSplitPane7 = new JSplitPane();
            upperSplitPane7.setResizeWeight(0.5);
            upperSplitPane7.setOrientation(HORIZONTAL_SPLIT);
            upperSplitPane7.setRightComponent(form_id);
            upperSplitPane7.setLeftComponent(form);
            upperSplitPane7.setEnabled(false);

            getUpperPanel().add(upperSplitPane7);
            getUpperPanel().setLayout(new GridLayout(1,2, 150, 200));
            getUpperPanel().setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
            setVisible(true);
        }


}
