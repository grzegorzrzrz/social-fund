package panels;

import classes.Penalty;
import lib.BasePanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;
@Getter

public class ChoosePenaltyPanel extends BasePanel {

    JComboBox penaltyBox;
    Vector<String> descriptionVec;
    JButton addPenaltyButton;
    Vector<Integer> penaltyIDVec;
    public ChoosePenaltyPanel(ArrayList<Penalty> penalties)
    {
        JLabel explanationText = new JLabel("Choose if a user should receive a penalty while returning this book");
        addPenaltyButton = new JButton("Add the penalty to the user");
        explanationText.setPreferredSize(new Dimension(100, 50));
        getAcceptButton().setText("Return the book without any penalty");

        Vector<String> namesVector = new Vector<>();
        penaltyIDVec = new Vector<>();
        descriptionVec = new Vector<>();

        for(Penalty penalty: penalties)
        {
            penaltyIDVec.add(penalty.getPenaltyID());
            namesVector.add(penalty.getName());
            descriptionVec.add(penalty.getDescription());
        }
        penaltyBox = new JComboBox(namesVector);
        penaltyBox.setToolTipText("choose if a user should receive a penalty");
        penaltyBox.setRenderer(new ToolTipComboBoxRenderer());

        penaltyBox.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        explanationText.setAlignmentX(Component.CENTER_ALIGNMENT);
        getUpperPanel().add(explanationText);
        getUpperPanel().add(Box.createVerticalGlue());
        getUpperPanel().add(penaltyBox);
        addPenaltyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getUpperPanel().add(addPenaltyButton);
        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(),BoxLayout.Y_AXIS));
        setVisible(true);
    }

    /**
     * All this class does, is make it possible to get penalties description while hovering over them in JComboBox
     */
    class ToolTipComboBoxRenderer extends BasicComboBoxRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
                if (-1 < index) {

                    list.setToolTipText(descriptionVec.elementAt(index));
                }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
}
