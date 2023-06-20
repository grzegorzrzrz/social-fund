package panels;

import lib.BasePanel;
import lib.InteractiveJTextField;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

@Getter
public class AddFormPanel extends BasePanel {
    public JTable formTable;

    private Boolean creatingPanel = true;

    private InteractiveJTextField formName;

    private DefaultTableModel formTableModel;

    private JComboBox fundComboBox;
    public AddFormPanel(String[] fundTypes){
        formTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };


        formTableModel.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                if(creatingPanel) return;
                if(e.getType() == TableModelEvent.UPDATE)
                {
                    int lastRow = e.getLastRow();
                    if(lastRow + 1 == formTableModel.getRowCount() )
                    {
                        if(!IsTableRowEmpty(lastRow))
                            formTableModel.addRow(new Object[]{null,null,null,null});
                    }
                    else if(IsTableRowEmpty(lastRow))
                    {
                        formTableModel.removeRow(lastRow);
                    }
                }
            }
        });

        JPanel upperPanel = new JPanel();
        formTable = new JTable(formTableModel);
        formTable.getTableHeader().setReorderingAllowed(false);

        formTableModel.addColumn("name");
        formTableModel.addColumn("type");
        formTableModel.addColumn("value");
        formTableModel.addColumn("max length");

        formTableModel.addRow(new Object[]{null,null,null,null});
        JScrollPane scrollPane = new JScrollPane(formTable);
        formName = new InteractiveJTextField("Type the name of the new form");
        fundComboBox = new JComboBox(fundTypes);
        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setEnabled(false);
        upperPanel.add(formName);
        upperPanel.add(fundComboBox);
        upperPanel.setLayout(new GridLayout(2,1,10,10));
        splitPane.setLeftComponent(upperPanel);
        splitPane.setRightComponent(scrollPane);
        getUpperPanel().add(splitPane);
        creatingPanel = false;
        setVisible(true);
        }

        boolean IsTableRowEmpty(int row)
        {
            for (var element:
                    formTableModel.getDataVector().elementAt(row)) {
                if(element != null && !element.toString().isEmpty()) return false;
            }
            return true;
        }
}
