package panels;
import classes.Book;
import lib.BasePanel;
import lib.InteractiveJTextField;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
@Getter
public class RegisterBookPanel extends BasePanel {
    private InteractiveJTextField BookTitleInput;
    private JTextArea BookTitle;

    private InteractiveJTextField BookAuthorInput;
    private JTextArea BookAuthor;
    private InteractiveJTextField BookPagesInput;
    private JTextArea BookPages;
    private InteractiveJTextField BookYearInput;
    private JTextArea BookYear;
    private InteractiveJTextField BookGenreInput;
    private JTextArea BookGenre;

    private InteractiveJTextField BookISBNInput;
    private JTextArea BookISBN;
    /**create panel which register new book */
    public RegisterBookPanel() {
        BookTitle = new JTextArea("BookTitle:");
        BookTitle.setEditable(false);
        BookTitleInput = new InteractiveJTextField("Type the BookTitle");
        setVisible(true);
        Book book =new Book(1,"a","A",67,"a",2000,"fantasy");
        JSplitPane upperSplitPane = new JSplitPane();

        upperSplitPane.setResizeWeight(0.5);
        upperSplitPane.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane.setRightComponent(BookTitleInput);
        upperSplitPane.setLeftComponent(BookTitle);
        upperSplitPane.setEnabled(false);
        Dimension minimumSize = new Dimension(100, 100);
        BookTitleInput.setMinimumSize(minimumSize);
        BookTitle.setMinimumSize(minimumSize);
        getAcceptButton().setText("add book");
        getUpperPanel().setLayout(new BoxLayout(getUpperPanel(),BoxLayout.Y_AXIS));
        getUpperPanel().add(upperSplitPane);



        BookAuthor = new JTextArea("BookAuthor:");
        BookAuthor.setEditable(false);
        BookAuthorInput = new InteractiveJTextField("Type the BookAuthor");
        JSplitPane upperSplitPane3 = new JSplitPane();
        upperSplitPane3.setResizeWeight(0.5);
        upperSplitPane3.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane3.setRightComponent(BookAuthorInput);
        upperSplitPane3.setLeftComponent(BookAuthor);
        upperSplitPane3.setEnabled(false);
        BookAuthorInput.setMinimumSize(minimumSize);
        BookAuthor.setMinimumSize(minimumSize);
        getUpperPanel().add(upperSplitPane3);

        BookISBN = new JTextArea("BookISBN:");
        BookISBN.setEditable(false);
        BookISBNInput = new InteractiveJTextField("Type the BookISBN");
        JSplitPane upperSplitPane7 = new JSplitPane();
        upperSplitPane7.setResizeWeight(0.5);
        upperSplitPane7.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane7.setRightComponent(BookISBNInput);
        upperSplitPane7.setLeftComponent(BookISBN);
        upperSplitPane7.setEnabled(false);
        BookISBNInput.setMinimumSize(minimumSize);
        BookISBN.setMinimumSize(minimumSize);
        getUpperPanel().add(upperSplitPane7);

        BookPages = new JTextArea("BookPages:");
        BookPages.setEditable(false);
        BookPagesInput = new InteractiveJTextField("Type the BookPages");
        JSplitPane upperSplitPane4 = new JSplitPane();
        upperSplitPane4.setResizeWeight(0.5);
        upperSplitPane4.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane4.setRightComponent(BookPagesInput);
        upperSplitPane4.setLeftComponent(BookPages);
        upperSplitPane4.setEnabled(false);
        BookPagesInput.setMinimumSize(minimumSize);
        BookPages.setMinimumSize(minimumSize);
        getUpperPanel().add(upperSplitPane4);

        BookYear = new JTextArea("BookYear:");
        BookYear.setEditable(false);
        BookYearInput = new InteractiveJTextField("Type the BookYear");
        JSplitPane upperSplitPane5 = new JSplitPane();
        upperSplitPane5.setResizeWeight(0.5);
        upperSplitPane5.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane5.setRightComponent(BookYearInput);
        upperSplitPane5.setLeftComponent(BookYear);
        upperSplitPane5.setEnabled(false);
        BookYearInput.setMinimumSize(minimumSize);
        BookYear.setMinimumSize(minimumSize);
        getUpperPanel().add(upperSplitPane5);


        BookGenre = new JTextArea("BookGenre:");
        BookGenre.setEditable(false);
        BookGenreInput = new InteractiveJTextField("Type the BookGenre");
        JSplitPane upperSplitPane6 = new JSplitPane();
        upperSplitPane6.setResizeWeight(0.5);
        upperSplitPane6.setOrientation(HORIZONTAL_SPLIT);
        upperSplitPane6.setRightComponent(BookGenreInput);
        upperSplitPane6.setLeftComponent(BookGenre);
        upperSplitPane6.setEnabled(false);
        BookGenreInput.setMinimumSize(minimumSize);
        BookGenre.setMinimumSize(minimumSize);
        getUpperPanel().add(upperSplitPane6);


        setVisible(true);

    }
}
