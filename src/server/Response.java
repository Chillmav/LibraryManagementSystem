package server;

import classes.users.User;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Response {


    private static String corsHeaders(Request request) {

        return "Access-Control-Allow-Origin: %s\r\n".formatted(request.getHostAddress()) +
                "Access-Control-Allow-Credentials: true\r\n" +
                "Access-Control-Allow-Headers: Content-Type, Authorization\r\n" +
                "Access-Control-Allow-Methods: GET, POST, OPTIONS\r\n";

    }

    public static String getResponse(Request request, String status, String message) {


        String method = request.getMethod();

        if (method.equalsIgnoreCase("OPTIONS")) {

            return "HTTP/1.1 " + status + " No Content\r\n" +
                    corsHeaders(request) +
                    "Content-Length: 0\r\n" +
                    "\r\n";

        } else if (method.equalsIgnoreCase("POST")) {

            String body = "{\"message\":\"" + message + "\"}";
            int content_length = body.getBytes(StandardCharsets.UTF_8).length;
            return "HTTP/1.1 " + status + " OK\r\n" +
                    corsHeaders(request) +
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



    public static String unauthorized(Request request, String message) {

        String body = "{\"status\":\"failure\",\"message\":\"%s\"}".formatted(message);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 401 Unauthorized\r\n" +
                 corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }


    public static String successfulLogin(Request request, UUID sessionId) {

        String body = "{\"status\":\"success\",\"message\":\"Login successful!\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(request) +
                "Set-Cookie: SESSIONID=" + sessionId.toString() +
                "; Path=/; HttpOnly; SameSite=None; Secure\r\n"
                +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }


    public static String logout(Request request) {

        String body = "{\"status\":\"success\",\"message\":\"User logged out successfully.\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(request) +
                "Set-Cookie: SESSIONID=;" + "Max-Age=0;" +
                "Path=/; HttpOnly; SameSite=None; Secure\r\n"
                +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }


    public static String getLibraryBooks(Request request, String books) {

        String body = "[%s]".formatted(books);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;


    }

    public static String badRequest(Request request) {

        return "HTTP/1.1 400 Bad Request\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n\r\n";
    }

    public static String success(Request request) {

        String body = "{\"status\":\"success\",\"message\":\"Success\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;


    }

    public static String failure(Request request) {

        String body = "{\"status\":\"failure\",\"message\":\"Failure\"}";
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 400 Bad Request\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;


    }
    public static String registerFailure(Request request, String message) {

        String body = "{\"status\":\"Failure\",\"message\":\"%s\"}".formatted(message);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 400 Bad Request\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }

    public static String registerSuccess(Request request, String message) {

        String body = "{\"status\":\"Success\",\"message\":\"%s\"}".formatted(message);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(request) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;

    }

    public static String sendSessionTime(Request req, String time) {

        String body = "{\"status\":\"Success\",\"message\":\"%s\"}".formatted(time);
        int length = body.getBytes(StandardCharsets.UTF_8).length;

        return "HTTP/1.1 200 OK\r\n" +
                corsHeaders(req) +
                "Connection: close\r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n" +
                body;
    }




}
