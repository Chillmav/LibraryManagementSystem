package server;

import classes.users.User;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Response {

    private static String corsHeaders() {

        return "Access-Control-Allow-Origin: http://localhost:5173\r\n" +
                "Access-Control-Allow-Credentials: true\r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization\r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS\r\n";

    }

    public static String getResponse(Request request, String status, String message) {


        String method = request.getMethod();

        if (method.equalsIgnoreCase("OPTIONS")) {

            return "HTTP/1.1 " + status + " No Content\r\n" +
                    corsHeaders() +
                    "Content-Length: 0\r\n" +
                    "\r\n";

        } else if (method.equalsIgnoreCase("POST")) {

            String body = "{\"message\":\"" + message + "\"}";
            int content_length = body.getBytes(StandardCharsets.UTF_8).length;
            return "HTTP/1.1 " + status + " OK\r\n" +
                    corsHeaders() +
                    "Connection: close\r\n" +
                    "Content-Length: " + content_length +"\r\n" +
                    "\r\n" +
                    body;

        } else {
            return "";
        }
    }

    public static String getResponse(Request request, String status) {
        return getResponse(request, status, "");
    }



    public static String unauthorized(String message) {

        String body = "{\"status\":\"failure\",\"message\":\"%s\"}".formatted(message);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 401 Unauthorized\r\n" +
                 corsHeaders() +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }


    public static String successfulLogin(Request request, UUID sessionId) {

        String body = "{\"status\":\"success\",\"message\":\"Login successful!\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders() +
                "Set-Cookie: SESSIONID=" + sessionId.toString() +
                "; Path=/; HttpOnly; SameSite=None\r\n"
                +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }

    public static String unsuccessfulLogin(Request request) {

        String body = "{\"status\":\"failure\",\"message\":\"Invalid email or password.\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 401 Unauthorized\r\n" +
                    corsHeaders() +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }


    public static String getLibraryBooks(Request request, String books) {

        String body = "{\"books\": " + books + "}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders() +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;


    }

    public static String badRequest(Request request) {

        return "HTTP/1.1 400 Bad Request\r\n" +
                corsHeaders() +
                "Connection: close\r\n\r\n";
    }




}
