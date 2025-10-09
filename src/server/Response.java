package server;

public class Response {


    public static String getResponse(String method, String status, String message) {

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
                    "\r\n" +
                    "{\"message\":\"" + message + "\"}";

        } else {
            return "";
        }
    }

    public static String getResponse(String method, String status) {
        return getResponse(method, status, "");
    }



}
