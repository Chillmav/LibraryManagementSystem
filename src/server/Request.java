package server;

import classes.users.Reader;
import utils.PathMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Request {

    private String method;
    private String path;
    private String headers;
    private String body;
    private UUID userId;
    private String hostAdress;
    private Map<String, String> queryParams;
    public String getHostAddress() {
        return hostAdress;
    }


    public Request(BufferedReader reader) {

        try {
            boolean status = checkReader(reader);

            if(status) {

                PathMethodParams methodPathQueryParams = extractMethodPathQueryParams(reader);
                String[] headersAndBody = extractHeadersBodyAndCookies(reader);
                String userIdStr = headersAndBody[2];
                if (userIdStr != null && !userIdStr.isEmpty()) {
                    this.userId = UUID.fromString(userIdStr);
                } else {
                    this.userId = null; // no session yet
                }

                this.method = methodPathQueryParams.method();
                this.path = methodPathQueryParams.path();
                this.queryParams = methodPathQueryParams.queryParams();
                this.headers = headersAndBody[0];
                this.body = headersAndBody[1];
                this.hostAdress = headersAndBody[3];

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String[] extractHeadersBodyAndCookies(BufferedReader reader) throws IOException {

        StringBuilder headers = new StringBuilder();
        String line;
        String userId = "";
        String hostAdress = "";

        int bodyLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {

            headers.append(line).append("\n");
            if (line.startsWith("Content-Length:")) {

                bodyLength = Integer.parseInt(line.substring(line.indexOf(" ") + 1).trim());


            }

            if (line.startsWith("Cookie:")) {

                String[] cookies = line.substring("Cookie:".length()).split(";");
                for (String cookie : cookies) {
                    cookie = cookie.trim();
                    if (cookie.startsWith("SESSIONID=")) {
                        userId = cookie.substring("SESSIONID=".length()).trim();
                        break;
                    }
                }
            }

            if (line.startsWith("Origin: ")) {

                hostAdress = line.substring(8).trim();

            }


        }

        char[] bodyChars = new char[bodyLength];
        reader.read(bodyChars, 0, bodyLength);


        return new String[]{String.valueOf(headers), String.valueOf(bodyChars), userId, hostAdress};
    }

    private PathMethodParams extractMethodPathQueryParams(BufferedReader reader) throws IOException{

        String firstLine = reader.readLine();
        String[] parts = firstLine.split(" ");
        String path = "";
        Map<String, String> queryParams = new HashMap<>();

        if (parts[1].contains("?")) {
            path = parts[1].substring(0, parts[1].indexOf("?"));
            String[] params = parts[1].substring(parts[1].indexOf("?") + 1).split("&");
            for (var param : params) {

                String[] keyValue = param.split("=", 2);
                if (keyValue.length > 2) {
                    throw new RuntimeException("Wrong http request");
                }
                queryParams.put(keyValue[0], keyValue[1]);

            }
        } else {
            path = parts[1];
        }
        return new PathMethodParams(parts[0], parts.length > 1 ? path : "/", queryParams);
    }

    private boolean checkReader(BufferedReader reader) throws IOException {

        reader.mark(1);

        if (reader.read() == -1) {
            System.out.println("Reader is empty");
            return false;
        } else {
            reader.reset();
            return true;
        }

    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }
    public UUID getUserId() {return userId;}


}
