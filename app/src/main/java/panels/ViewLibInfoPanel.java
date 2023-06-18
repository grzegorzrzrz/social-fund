package panels;

import classes.Library;
import lib.BasePanel;
import lib.Settings;
import lombok.Getter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

@Getter
public class ViewLibInfoPanel extends BasePanel {
    private JComboBox<String> chooseLibrary;
    private JTable libraryInfo;
    private DefaultTableModel LibraryInfoTableModel;
    private JScrollPane scrollPane;
    private JLabel statusInfo;
    public ViewLibInfoPanel(){
        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);

        statusInfo = new JLabel("Status: Waiting for change");
        chooseLibrary = new JComboBox<>(Settings.getInstance().mockDatabase.getLibrariesNames());
        LibraryInfoTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        libraryInfo = new JTable(LibraryInfoTableModel);
        chooseLibrary.setPreferredSize(new Dimension(300, 100));
        statusInfo.setPreferredSize(new Dimension(300, 100));
        libraryInfo.setPreferredSize(new Dimension(500, 500));
        JSplitPane upperSplitPane = new JSplitPane();
        upperSplitPane.setResizeWeight(0.5);
        upperSplitPane.setAlignmentX(LEFT_ALIGNMENT);
        upperSplitPane.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane.setLeftComponent(chooseLibrary);
        upperSplitPane.setRightComponent(statusInfo);
        upperSplitPane.setDividerSize(50);
        upperSplitPane.setEnabled(false);
        Dimension minimumSize = new Dimension(100, 100);
        chooseLibrary.setMinimumSize(minimumSize);
        statusInfo.setMinimumSize(minimumSize);


        scrollPane = new JScrollPane(libraryInfo);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        getBottomPanel().remove(getAcceptButton());
        getUpperPanel().add(upperSplitPane);
        getUpperPanel().add(scrollPane);
        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(),BoxLayout.Y_AXIS));
        getLibraryInfoTableModel().setColumnCount(2);
        scrollPane.getColumnHeader().setVisible(false);
        setVisible(true);

        fillLibraryInfo(Settings.getInstance().mockDatabase.getLibraryInfo(chooseLibrary.getSelectedItem().toString()));
    }

    /**
     * @param library Library from database method
     *                fills the JTable with info from library object
     */
    public void fillLibraryInfo(Library library)
    {
        getLibraryInfoTableModel().setRowCount(0);


        getLibraryInfoTableModel().addRow(new Object[] {"Library name: ", library.getName()});
        getLibraryInfoTableModel().addRow(new Object[] {"Street: ", library.getStreet()});
        getLibraryInfoTableModel().addRow(new Object[] {"City: ", library.getCity()});
        getLibraryInfoTableModel().addRow(new Object[] {"Phone: ", library.getPhone()});
        getLibraryInfoTableModel().addRow(new Object[] {"Work Times:"});

        for (int i = 0; i < library.getWorkTimes().getOpening().size(); i++) {
            getLibraryInfoTableModel().addRow(new Object[] {library.getWorkTimes().getOpening().get(i),
                    library.getWorkTimes().getClosing().get(i)});
        }
    }
}
