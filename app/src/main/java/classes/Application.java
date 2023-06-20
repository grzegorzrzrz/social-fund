package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
@Setter
public class Application{
    public Application()
    {
        applicant = new Applicant();
        status="Oczekujacy";
        creationDate=new Date(System.currentTimeMillis());
        form= new Form();
    };

    public Applicant applicant;
    public String status;
    public Date creationDate;
    public Form form;
}
