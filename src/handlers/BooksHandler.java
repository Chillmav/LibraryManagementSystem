package handlers;

import classes.Library;
import classes.users.Reader;
import server.Request;
import server.SessionManager;

import java.sql.Connection;

public class BooksHandler {

    public static String getLibraryBooks(Request req, Connection conn, SessionManager sessionManager) {

        String booksToDisplay = Library.displayBooks(conn);
        System.out.println(booksToDisplay);

        return "";

    }
}
