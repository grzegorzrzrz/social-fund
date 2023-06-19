package panels;

import lib.BasePanel;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.sql.Date;
import java.util.Vector;
@Getter
public class ChooseApplicationToView extends BasePanel {


    JComboBox applicationsBox;
    Vector<Integer> idVec;
    Vector<Date> dateVec;
    public ChooseApplicationToView(Vector<Pair<Integer, Date>> dataVector)
    {

        JLabel explanationText = new JLabel("Choose an application To View");
        explanationText.setPreferredSize(new Dimension(100, 50));
        getAcceptButton().setText("View this application");

        idVec = new Vector<>();
        dateVec = new Vector<>();

        for(Pair data: dataVector)
        {
            idVec.add((Integer) data.getKey());
            dateVec.add((Date) data.getValue());
            }
        applicationsBox = new JComboBox(idVec);
        applicationsBox.setToolTipText("choose which application to view");
        applicationsBox.setRenderer(new ChooseApplicationToView.ToolTipComboBoxRenderer());

        applicationsBox.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        explanationText.setAlignmentX(Component.CENTER_ALIGNMENT);
        getUpperPanel().add(explanationText);
        getUpperPanel().add(Box.createVerticalGlue());
        getUpperPanel().add(applicationsBox);
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

                    list.setToolTipText(dateVec.elementAt(index).toString());
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
