package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
@Setter
public class User {
    public User()
    {
        name="Jan";
        surname="Kowalski";
        login="login";
        password="password";
        permissionLevel=0;
        userID=0;
    }
    String name;
    String surname;
    String login;
    String password;
    // permission level 0 - not logged in
    // permission level 1 - logged in as user
    // permission level 2 - logged as employee
    private int permissionLevel;
    private int userID;
}
