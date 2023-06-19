package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class FormField{
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
