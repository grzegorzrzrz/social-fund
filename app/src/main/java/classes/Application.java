package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Application {
    public Application()
    {
        status="denied";
        creationDate=new Date(2023,11,3);
        form= new Form();
    };

    public String status;
    public Date creationDate;
    public Form form;
}
