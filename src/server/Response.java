package server;

import classes.users.User;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Response {


    public static String getResponse(Request request, String status, String message) {


        String method = request.getMethod();

        if (method.equalsIgnoreCase("OPTIONS")) {

            return "HTTP/1.1 " + status + " No Content\r\n" +
                    "Access-Control-Allow-Origin: *\r\n" +
                    "Access-Control-Allow-Methods: GET, POST, OPTIONS\r\n" +
                    "Access-Control-Allow-Headers: Content-Type, Authorization\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n";

        } else if (method.equalsIgnoreCase("POST")) {

            return "HTTP/1.1 " + status + " OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Access-Control-Allow-Origin: *\r\n" +
                    "Connection: close\r\n" +
                    "Content-Length: 0\r\n" +
                    "\r\n" +
                    "{\"message\":\"" + message + "\"}";

        } else {
            return "";
        }
    }

    public static String getResponse(Request request, String status) {
        return getResponse(request, status, "");
    }


    public static String successfulLogin(Request request, UUID sessionId) {

        String body = "{\"status\":\"success\",\"message\":\"Login successful!\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "Access-Control-Allow-Origin: *\r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization\r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS\r\n" +
                "Set-Cookie: SESSIONID=" + sessionId.toString() + "; Path=/; HttpOnly\r\n" +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }

}
