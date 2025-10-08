import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class HTTPServer {

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(9000)) {
            System.out.println("Server running on http://localhost:9000");
            
            while (true) {

                Socket client = serverSocket.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));  // Read data from the input stream (from client)
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream())); // Write data to the client
                String resource = "";

                while (true) {
                    String line = reader.readLine();

                    System.out.println("Header: " + line);

                    if (line.isEmpty()) {
                        break;
                    }

                    if (line.contains("GET")) {
                        int startIndex = line.indexOf("GET /");
                        int lastIndex = line.indexOf(" HTTP");

                        resource = line.substring(startIndex + 5, lastIndex);
                    }


                }

                System.out.println("Resource: " + resource);

            }
        } catch (Exception e) {

            e.getStackTrace();

        }


    }

}
