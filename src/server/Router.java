package server;

import handlers.UserHandler;

import java.sql.Connection;

public class Router {


    public static void route(Request req, Connection conn) {

        if (!(req.getMethod().equalsIgnoreCase("OPTIONS"))) {

            switch (req.getPath()) {

                case "/login":

                    UserHandler.handleLogin(req, conn);
                    break;

                case "/register":

                    UserHandler.handleRegister(req);
                    break;

                default:
                    break;
            }
        }

    }
}
