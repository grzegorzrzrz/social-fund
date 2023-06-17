package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Form {
    int formTypeID;
    String name;
    ArrayList<FormField> fields;
}
