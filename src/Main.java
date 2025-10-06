import classes.Library;
import classes.Role;
import classes.users.Employee;
import classes.users.Manager;
import classes.users.Reader;
import classes.users.User;
import utils.UserService;

import javax.swing.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";
    private final static String USER = "root";
    private final static Library library = new Library("MyLibrary");

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        System.out.println("Provide password to library database: ");
        String password = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection(CONN_STRING, USER, password)) {

            System.out.println("Success!! Connection made to the music database");
            User user = null;

            while (user == null) {

                System.out.println("Log in (1), Register(2), Exit(3)");
                String optionString = scanner.nextLine();
                int option = Integer.parseInt(optionString);

                switch (option) {

                    case 1: {

                        user = UserService.login(conn, scanner);
                        if (user != null) {
                            System.out.println("Welcome, you logged in successfully");
                            user = switch (user.getRole()) {
                                case READER ->
                                        new Reader(user.getId(), user.getRole(), user.getFirstName(), user.getSecondName());
                                case MANAGER ->
                                        new Manager(user.getId(), user.getRole(), user.getFirstName(), user.getSecondName());
                                case EMPLOYEE ->
                                        new Employee(user.getId(), user.getRole(), user.getFirstName(), user.getSecondName());
                            };
                            break;
                        }
                        break;
                    }

                    case 2:

                        UserService.register(conn, scanner);
                        break;
                    case 3:
                        return;

                    default:
                        System.out.println("Wrong option");
                }
                if (user != null) {


                    System.out.printf("You logged in as a %s \n", user.getClass().getSimpleName());

                    int userChoice = 1;
                    while (userChoice != 5) {
                        userChoice = user.displayPanel(scanner, library, conn);

                    }
                    user = null;




                }





            }


            } catch (SQLException e) {
            throw new RuntimeException(e);


        }

        scanner.close();
    }
}
