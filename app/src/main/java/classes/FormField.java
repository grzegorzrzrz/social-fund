package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Vector;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class FormField{

    public FormField(Vector vec)
    {
        name = (String) vec.elementAt(0);
        type = (String) vec.elementAt(1);
        value = (String) vec.elementAt(2);
        maximumLength = (int)vec.elementAt(3);
    }
    public FormField()
    {
        name="ala";
        type="cos";
        value="7";
        maximumLength=9;
    }
    String name;
    String type;
    public String value;
    int maximumLength;
}
