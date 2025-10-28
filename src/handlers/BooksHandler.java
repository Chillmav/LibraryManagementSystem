package handlers;

import classes.Library;
import classes.Role;
import classes.users.Reader;
import classes.users.User;
import server.Request;
import server.Response;
import server.SessionManager;
import utils.SessionResult;

import java.sql.Connection;
import java.util.UUID;

public class BooksHandler {

    public static String borrowBook(Request req, Connection conn, SessionManager sessionManager) {

        UUID uuid = req.getUserId();
        System.out.println("BooksHandler(borrowBook invoked for userId: " + uuid);

        if (uuid == null) {

            System.out.println("User ID is null — possibly not logged in or missing session.");
            return Response.unauthorized(req,"User not logged in");

        }

        User user = sessionManager.getSessionIdMap().get(uuid).getUser();
        if (user.getRole() == Role.READER) {
            String booksToDisplay = user.borrowBook(conn, req);
            System.out.println(booksToDisplay);
            return booksToDisplay;
        } else {
            return Response.getResponse(req, "400", "Employee can't borrow books");
        }



    }

    public static String returnBook(Request req, Connection conn, SessionManager sessionManager) {

        UUID uuid = req.getUserId();
        System.out.println("BooksHandler(returnBook invoked for userId: " + uuid);

        if (uuid == null) {

            System.out.println("User ID is null — possibly not logged in or missing session.");
            return Response.unauthorized(req,"User not logged in");

        }

        User user = sessionManager.getSessionIdMap().get(uuid).getUser();
        String booksToDisplay = user.returnBook(conn, req);
        System.out.println(booksToDisplay);

        return booksToDisplay;
    }


    public static String getUserBooks(Request req, Connection conn, SessionManager sessionManager) {

        UUID uuid = req.getUserId();
        System.out.println("BooksHandler(getUserBooks invoked for userId: " + uuid);

        if (uuid == null) {

            System.out.println("User ID is null — possibly not logged in or missing session.");
            return Response.unauthorized(req,"User not logged in");

        }

        if (sessionManager.getSessionIdMap().get(uuid) != null) {

            String booksToDisplay = Library.displayUserBorrowedBooks(sessionManager.getSessionIdMap().get(uuid).getUser().getId(), conn);
            System.out.println(booksToDisplay);

            return Response.getLibraryBooks(req, booksToDisplay);

        } else {

            return "";

        }
    }
    public static String getLibraryBooks(Request req, Connection conn, SessionManager sessionManager) {

        UUID uuid = req.getUserId();
        System.out.println("BooksHandler invoked for userId: " + uuid);
        if (uuid == null) {
            System.out.println("User ID is null — possibly not logged in or missing session.");
            return Response.unauthorized(req,"User not logged in");
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
