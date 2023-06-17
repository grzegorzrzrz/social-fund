package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class FormField {
    int fieldID;
    String name;
    String type;
    String value;
    int maximumLength;
}
