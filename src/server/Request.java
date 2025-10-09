package server;

import classes.users.Reader;

import java.io.BufferedReader;
import java.io.IOException;

public class Request {

    private String method;
    private String path;
    private String headers;
    private String body;

    public Request(BufferedReader reader) {

        try {
            boolean status = checkReader(reader);

            if(status) {

                String[] methodAndPath = extractMethodAndPath(reader);
                String[] headersAndBody = extractHeadersAndBody(reader);

                this.method = methodAndPath[0];
                this.path = methodAndPath[1];
                this.headers = headersAndBody[0];
                this.body = headersAndBody[1];

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String[] extractHeadersAndBody(BufferedReader reader) throws IOException {

        StringBuilder headers = new StringBuilder();
        String line;
        String body;
        int bodyLength = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {

            headers.append(line).append("\n");
            if (line.startsWith("Content-Length:")) {

                bodyLength = Integer.parseInt(line.substring(line.indexOf(" ") + 1).trim());


            }
        }

        char[] bodyChars = new char[bodyLength];
        reader.read(bodyChars, 0, bodyLength);


        return new String[]{String.valueOf(headers), String.valueOf(bodyChars)};
    }

    private String[] extractMethodAndPath(BufferedReader reader) throws IOException{

        String firstLine = reader.readLine();
        String[] parts = firstLine.split(" ");

        return new String[]{parts[0], parts.length > 1 ? parts[1] : "/"};
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
}
