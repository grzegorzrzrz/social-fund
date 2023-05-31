package classes;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter
public class Book {
    int bookID;
    String title;
    String author;
    int pages;
    String ISBN;
    int year;
    String genre;
}
