package panels;

import lib.BasePanel;
import lib.InteractiveJTextField;
import lib.Settings;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

import classes.Application;

import java.util.ArrayList;

public class fillAplication extends BasePanel {

    public ArrayList<InteractiveJTextField> dane;
    private ArrayList<JLabel> opis;
    public ArrayList<JSplitPane> pane;
    private InteractiveJTextField a;
    public fillAplication(Application application) {
        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
        dane=new ArrayList<InteractiveJTextField>();
        opis =new ArrayList<JLabel>();
        pane =new ArrayList<JSplitPane>();
        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);
        for (int i=0;i<application.form.getFields().size();i++)
        {
            a = new InteractiveJTextField("Plese fill gap");
          JLabel b=new JLabel(application.getForm().getFields().get(i).getName());
          JSplitPane c= new JSplitPane();
          dane.add(a);
          opis.add(b);
          pane.add(c);

          pane.get(i).setResizeWeight(0.5);
          pane.get(i).setOrientation(HORIZONTAL_SPLIT);
          pane.get(i).setRightComponent(dane.get(i));
          pane.get(i).setLeftComponent(opis.get(i));
          pane.get(i).setEnabled(false);
          getUpperPanel().add(pane.get(i));

        }
        getUpperPanel().setLayout(new GridLayout(application.form.getFields().size(), 2,100,100));
        setVisible(true);
    }

}