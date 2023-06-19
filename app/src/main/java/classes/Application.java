package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
@Setter
public class Application{
    public Application()
    {
        applicant = new Applicant();
        status="denied";
        creationDate=new Date(2023,11,3);
        form= new Form();
    };

    public Applicant applicant;
    public String status;
    public Date creationDate;
    public Form form;
}
