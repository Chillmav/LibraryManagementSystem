package server;

import handlers.BooksHandler;
import handlers.UserHandler;

import java.sql.Connection;

public class Router {


    public static String route(Request req, Connection conn, SessionManager sessionManager) throws Exception {


        String method = req.getMethod();

        if (method == null) {
            return Response.badRequest(req);
        }

        if (!(req.getMethod().equalsIgnoreCase("OPTIONS"))) {

            return switch (req.getPath()) {

                case "/login" -> UserHandler.handleLogin(req, conn, sessionManager);
                case "/register" -> UserHandler.handleRegister(req, conn);
                case "/library_books" -> BooksHandler.getLibraryBooks(req, conn, sessionManager);
                case "/user_books" -> BooksHandler.getUserBooks(req, conn, sessionManager);
                case "/logout" -> UserHandler.handleLogout(req, conn);
                default -> "";
            };

        } else {

            return Response.getResponse(req, "200");

        }

    }
}
