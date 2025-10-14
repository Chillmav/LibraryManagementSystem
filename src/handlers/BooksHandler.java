package handlers;

import classes.Library;
import classes.users.Reader;
import server.Request;
import server.Response;
import server.SessionManager;

import java.sql.Connection;
import java.util.UUID;

public class BooksHandler {

    public static String getLibraryBooks(Request req, Connection conn, SessionManager sessionManager) {

        UUID uuid = req.getUserId();
        System.out.println("BooksHandler invoked for userId: " + uuid);
        if (uuid == null) {
            System.out.println("User ID is null — possibly not logged in or missing session.");
            return Response.unauthorized("User not logged in");
        }

        if (sessionManager.getSessionIdMap().get(uuid) != null) {

            String booksToDisplay = Library.displayBooks(conn);
            System.out.println(booksToDisplay);

            return Response.getLibraryBooks(req, booksToDisplay);

        } else {

            return "";

        }

    }
}
