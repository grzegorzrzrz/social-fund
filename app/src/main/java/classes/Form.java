package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Form {
    public String name;
    public String fundName;
    public ArrayList<FormField> fields;
    public Form()
    {
        fields=new ArrayList<FormField>();
        FormField field= new FormField();
        fields.add(field);
        name="cos";
    }
}
