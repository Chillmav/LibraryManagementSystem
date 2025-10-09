package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class HTTPServer {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(9000)) {
            System.out.println("Server running on http://localhost:9000");

            while (true) {

                Socket client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));  // Read data from the input stream (from a client)
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); // Write data to the client
                Request request = new Request(reader);
                System.out.println(request.getBody());
                Router.route(request);
                writer.write(Response.getResponse(request.getMethod(), "200"));
                writer.flush();
                writer.close();
                client.close();
                reader.close();

            }
        } catch (Exception e) {

            e.getStackTrace();

        }


    }

}
