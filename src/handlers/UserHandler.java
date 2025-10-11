package handlers;

import classes.Library;
import classes.users.User;
import server.Request;
import utils.JsonUtils;

import java.sql.Connection;
import java.util.Map;

public class UserHandler {

    public static User handleLogin(Request req, Connection conn) {


        String body = req.getBody();

        Map<String, String> accountCredentials = JsonUtils.createMapFromJson(body);

        try {

            String email = accountCredentials.get("email");
            String password = accountCredentials.get("password");
            System.out.println(email);
            System.out.println(password);

            return Library.verifyUser(conn, email, password);



        } catch (Exception e) {
            e.getStackTrace();
        }

        return null;
    }

    public static void handleRegister(Request req) {
        String body = req.getBody();
    }
}
