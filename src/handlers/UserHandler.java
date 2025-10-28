package handlers;

import classes.Library;
import classes.users.User;
import server.Request;
import server.Response;
import server.SessionManager;
import utils.JsonUtils;
import utils.SessionResult;
import utils.UserSession;
import utils.UserVerification;

import java.sql.Connection;
import java.util.Map;

public class UserHandler {

    public static String handleLogin(Request req, Connection conn, SessionManager sessionManager) {


        String body = req.getBody();

        Map<String, String> accountCredentials = JsonUtils.createMapFromJson(body);

        try {

            String email = accountCredentials.get("email");
            String password = accountCredentials.get("password");
            UserVerification userVerification = Library.verifyUser(conn, email, password);

            User user = userVerification.user();

            if (user != null) {
                if (userVerification.isConfirmed()) {
                    SessionResult sr = sessionManager.handleSession(req, userVerification.user());
                    return Response.successfulLogin(req, sr.uuid());
                } else {
                    return Response.unauthorized(req, "Confirm your account!");
                }

            } else {

                return Response.unauthorized(req, "Credentials don't match to any accounts.");

            }



        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

    public static String handleRegister(Request req, Connection conn) {

        return Library.createReader(conn, req);

    }

    public static String confirmUser(Request req, Connection conn) {

    }
    public static String handleLogout(Request req, Connection conn) {

        return Response.logout(req);
    }

    public static String sendSessionTime(Request req, Connection conn, SessionManager sessionManager) {

        UserSession us = sessionManager.getSessionIdMap().get(req.getUserId());

        if (us != null) {
            long seconds = us.getRemainingTime();
            return Response.sendSessionTime(req, String.valueOf(seconds));
        } else {
            return Response.logout(req);
        }

    }
}
