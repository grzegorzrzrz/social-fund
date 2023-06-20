package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
@Setter
public class Form {
    public int formTypeID;
    public String name;
    public String fundName;
    public ArrayList<FormField> fields;

    public Form()
    {
        formTypeID = 0;
        fields=new ArrayList<FormField>();
        FormField field= new FormField();
        fields.add(field);
        name="cos";
    }
}
