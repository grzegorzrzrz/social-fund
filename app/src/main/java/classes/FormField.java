package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

//@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class FormField {
    public FormField(int id, String name2, String type2, String valu,int max)
    {
        fieldID=id;
        name=name2;
        type=type2;
        value=valu;
        maximumLength=max;
    }
    ;
    public FormField()
    {
        fieldID=5;
        name="ala";
        type="cos";
        value="7";
        maximumLength=9;
    }
    int fieldID;
    String name;
    String type;
    public String value;
    int maximumLength;
}
