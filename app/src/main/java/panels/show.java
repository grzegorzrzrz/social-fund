package panels;

import classes.Application;
import lib.BasePanel;
import lib.InteractiveJTextField;
import lib.Settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

public class show  extends BasePanel {


    private ArrayList<JTextField> dane;
    private ArrayList<JLabel> opis;
    private ArrayList<JSplitPane> pane;
    private JTextField a;

    public show(Application application) {

                setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
                setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
                dane=new ArrayList<JTextField>();
                opis =new ArrayList<JLabel>();
                pane =new ArrayList<JSplitPane>();
                setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);
                for (int i=0;i<application.form.getFields().size();i++)
                { a =new JTextField(application.getForm().getFields().get(i).getValue());
                    a.setEditable(false);
                    JLabel b=new JLabel(application.getForm().getFields().get(i).getName());
                    JSplitPane c= new JSplitPane();
                    dane.add(a);
                    opis.add(b);
                    pane.add(c);

                    pane.get(i).setResizeWeight(0.5);
                    pane.get(i).setOrientation(HORIZONTAL_SPLIT);
                    pane.get(i).setLeftComponent(opis.get(i));
                    pane.get(i).setRightComponent(dane.get(i));
                    pane.get(i).setEnabled(false);
                    getUpperPanel().add(pane.get(i));

                }
                getUpperPanel().setLayout(new GridLayout(application.form.getFields().size(), 2,100,100));
                setVisible(true);
                getAcceptButton().setVisible(false);
            }

        }


