package server;

import handlers.UserHandler;

public class Router {


    public static void route(Request req) {

        if (!(req.getMethod().equalsIgnoreCase("OPTIONS"))) {

            switch (req.getPath()) {

                case "/login":

                    UserHandler.handleLogin(req);
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
