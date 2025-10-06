package classes.interfaces;

import classes.Book;
import classes.users.Reader;

import java.sql.Connection;
import java.util.Scanner;

public interface Employee {

    void addBook(Connection conn, Scanner scanner);
    void removeBook(Connection conn, int bookId);
    void editBook(Connection conn, int bookId);
    Book createBook(Scanner scanner);
}
