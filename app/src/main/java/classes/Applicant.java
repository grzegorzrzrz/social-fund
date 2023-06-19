package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
@Setter
public class Applicant {
    public Applicant()
    {
        id="0";
        name="Jan";
        surname="Kowalski";
        company="PZU";
        pesel="12345678901";
        birthDate=new Date(2023,11,3);
        accountNumber=1234567890;
        earnings=1000;
    };
    String id;
    String name;
    String surname;
    String company;
    String pesel;
    Date birthDate;
    int accountNumber;
    int earnings;
}
