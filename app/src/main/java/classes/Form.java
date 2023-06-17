package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Form {
    public int formTypeID;
    public String name;
    public ArrayList<FormField> fields;
    public Form(int id,String name2)
    {
        fields=new ArrayList<FormField>();
        FormField field= new FormField(5,"number_of_product","int","7",9);
        fields.add(field);
        name=name2;
        formTypeID=id;

    }
}
