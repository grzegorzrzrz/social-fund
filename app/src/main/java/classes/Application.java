package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Application {
    public Application(int id,String stat,Date date)
    {
        Fund="ala";
        applicationID=id   ;
        status=stat;
        creationDate=date;
        form= new Form(5,"ala");

    };
    public Application()
    {
        Fund="ala";
        applicationID=1   ;
        status="deneid";
        creationDate=new Date(2023,11,3);
        form= new Form(5,"ala");

    };

    String Fund;
    public int applicationID;
    public String status;
    public Date creationDate;
    public Form form;
}
