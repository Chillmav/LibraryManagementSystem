package handlers;

import classes.Library;
import classes.users.User;
import server.Request;
import server.Response;
import server.SessionManager;
import utils.JsonUtils;
import utils.SessionResult;

import java.sql.Connection;
import java.util.Map;

public class UserHandler {

    public static String handleLogin(Request req, Connection conn, SessionManager sessionManager) {


        String body = req.getBody();

        Map<String, String> accountCredentials = JsonUtils.createMapFromJson(body);

        try {

            String email = accountCredentials.get("email");
            String password = accountCredentials.get("password");
            User user = Library.verifyUser(conn, email, password);

            if (user != null) {
                SessionResult sr = sessionManager.handleSession(req, user);
                return Response.successfulLogin(req, sr.uuid());
            } else {
                return Response.unsuccessfulLogin(req);
            }



        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

    public static String handleRegister(Request req, Connection conn) {
        String body = req.getBody();
        return "";
    }
}
