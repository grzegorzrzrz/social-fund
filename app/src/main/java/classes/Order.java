package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Order {
    public int OrderID;
    public String status;
    public String dateBorrow;
    public String dateReturn;
    public String bookTitle;
}
