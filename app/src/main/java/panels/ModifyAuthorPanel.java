package panels;

import lib.BasePanel;
import lib.InteractiveJTextField;
import lib.Settings;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
@Getter
public class ModifyAuthorPanel extends BasePanel {
    private JComboBox<String> chooseAuthors;
    private InteractiveJTextField AuthorBirthYear;
    private InteractiveJTextField AuthorNation;

    public ModifyAuthorPanel() {
//        setPreferredSize(Settings.getInstance().BIG_WINDOW_PREFERRED_SIZE);
//        setMinimumSize(Settings.getInstance().BIG_WINDOW_MIN_SIZE);
//        setLocation(Settings.getInstance().BIG_WINDOW_LOCATION_X, Settings.getInstance().BIG_WINDOW_LOCATION_Y);
//
//        chooseAuthors = new JComboBox<>(Settings.getInstance().mockDatabase.getAuthors());
//        AuthorBirthYear = new InteractiveJTextField("Type the birth date of author in format DD-MM-YYYY");
//        AuthorNation = new InteractiveJTextField("Type the nation of author");
//
//        chooseAuthors.setPreferredSize(new Dimension(300, 100));
//        AuthorBirthYear.setPreferredSize(new Dimension(300, 100));
//        AuthorNation.setPreferredSize(new Dimension(500, 500));
//
//        JSplitPane upperSplitPane = new JSplitPane();
//        upperSplitPane.setResizeWeight(0.5);
//        upperSplitPane.setOrientation(HORIZONTAL_SPLIT);
//
//        getUpperPanel().add(chooseAuthors);
//        getUpperPanel().add(AuthorBirthYear);
//        getUpperPanel().add(AuthorNation);
//        Dimension minimumSize = new Dimension(100, 100);
//        chooseAuthors.setMinimumSize(minimumSize);
//        AuthorBirthYear.setMinimumSize(minimumSize);
//        AuthorNation.setMinimumSize(minimumSize);
//        getAcceptButton().setText("Modify information");
//        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(), BoxLayout.Y_AXIS));
//
//        setVisible(true);
    }
}
