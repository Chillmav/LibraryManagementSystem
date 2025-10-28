package handlers;

import classes.Library;
import classes.Role;
import classes.users.User;
import server.Request;
import server.Response;
import server.SessionManager;
import utils.JsonUtils;
import utils.SessionResult;
import utils.UserSession;
import utils.UserVerification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class UserHandler {

    public static String handleLogin(Request req, Connection conn, SessionManager sessionManager) {


        String body = req.getBody();

        Map<String, String> accountCredentials = JsonUtils.createMapFromJson(body);

        try {

            String email = accountCredentials.get("email");
            String password = accountCredentials.get("password");
            UserVerification userVerification = Library.verifyUser(conn, email, password);

            if (userVerification == null) {
                return Response.unauthorized(req, "Credentials don't match to any accounts.");
            }
            User user = userVerification.user();

            if (user != null) {
                if (userVerification.isConfirmed()) {
                    SessionResult sr = sessionManager.handleSession(req, userVerification.user());
                    if (user.getRole() != Role.READER) {
                        return Response.successfulLogin(req, sr.uuid(), "Employee");
                    }
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

        String uuidString = req.getQueryParams().get("token");

        if (uuidString == null || uuidString.isEmpty()) {
            return Response.getResponse(req, "400", "Missing confirmation token.");
        }

        try (PreparedStatement psCheck = conn.prepareStatement("SELECT isConfirmed FROM users WHERE uuid = ?")) {

            psCheck.setString(1, uuidString);

            var rs = psCheck.executeQuery();
            if (rs.next()) {
                if (rs.getBoolean(1)) {
                    return Response.getResponse(req, "400", "Account is already confirmed");
                }
            } else {
                return Response.getResponse(req, "400", "Account doesn't exist");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement ps = conn.prepareStatement("UPDATE users " +
                "SET isConfirmed = 1 " +
                "WHERE uuid = ?")) {

            ps.setString(1, uuidString);

            int result = ps.executeUpdate();

            if (result > 0) {
                System.out.println("User confirmed successfully.");
                return Response.getResponse(req, "200", "Account confirmed.");
            } else {
                return Response.getResponse(req, "400", "Invalid or expired token");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
