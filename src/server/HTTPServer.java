package server;

import classes.Library;
import classes.Role;
import classes.users.User;
import utils.SessionResult;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.util.UUID;


public class HTTPServer {

    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";
    private final static String USER = "root";
//    private final static Library library = new Library("MyLibrary");
    private final static SessionManager sessionManager = new SessionManager();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Provide password to db: "); // haslodb
        String password = scanner.nextLine();
        try (Connection connection = DriverManager.getConnection(CONN_STRING, USER, password)) {

            try (ServerSocket serverSocket = new ServerSocket(9000)) {
                System.out.println("Server running on http://localhost:9000");

                while (true) {

                    Socket client = serverSocket.accept();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));  // Read data from the input stream (from a client)
                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); // Write data to the client
                    Request request = new Request(reader);

                    String responseMessage = Router.route(request, connection, sessionManager);

                    writer.write(responseMessage);
                    writer.flush();
                    client.close();
                    reader.close();

                }

            } catch (Exception e) {

                e.printStackTrace();

            }


        } catch (Exception e) {

            throw new RuntimeException(e);
        }


        }


}
