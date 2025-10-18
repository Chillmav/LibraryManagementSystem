package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

public class ClientHandler implements Runnable {



    private final Socket socket;
    private final SessionManager sessionManager;
    private final Connection connection;

    public ClientHandler(Socket socket, SessionManager sessionManager, Connection connection) {
        this.socket = socket;
        this.sessionManager = sessionManager;
        this.connection = connection;
    }

    @Override
    public void run() {

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));  // Read data from the input stream (from a client)
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            Request request = new Request(reader);
            String responseMessage = Router.route(request, connection, sessionManager);
            writer.write(responseMessage);
            writer.flush();
            socket.close();
            reader.close();

        } catch (Exception e) {

            throw new RuntimeException(e);

        }
    }
}
