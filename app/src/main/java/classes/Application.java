package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Application {
    String Fund;
    int applicationID;
    String status;
    Date creationDate;
    Form form;
}
