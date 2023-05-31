package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Library {
    int libraryID;
    String name;
    String street;
    String city;
    int phone;
    WorkTime WorkTimes;
}
