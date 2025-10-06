package utils;

import classes.Library;
import classes.users.User;
import com.mysql.cj.callback.UsernameCallback;

import java.sql.Connection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {

    private static final Pattern emailPattern = Pattern.compile(".+@.+[.][^.]+");

    public static void register(Connection conn, Scanner scanner) {

        System.out.println("First Name: ");
        String firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        String lastName = scanner.nextLine();
        int age = 0;
        while (true) {
            System.out.println("Age: ");
            String ageInput = scanner.nextLine();
            try {
                age = Integer.parseInt(ageInput);
                if (age <= 0) {
                    System.out.println("Age must be positive");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }

        String email;
        while (true) {
            System.out.println("Email: ");
            email = scanner.nextLine();
            Matcher matcher = emailPattern.matcher(email);
            if (matcher.matches()) {
                break;
            } else {
                System.out.println("Wrong email format, try again");
            }
        }

        String password;
        while (true) {
            System.out.println("Password: ");
            password = scanner.nextLine();
            if (password.length() < 8) {
                System.out.println("Your password must contain at least 8 characters");
            } else {
                break;
            }


        }



        Library.createReader(conn, firstName, lastName, age, email, password);

        System.out.println("We sent you an email, please confirm your account");

    }

    public static User login(Connection conn, Scanner scanner) {

        String email;
        while (true) {
            System.out.println("Email: ");
            email = scanner.nextLine();
            Matcher matcher = emailPattern.matcher(email);
            if (matcher.matches()) {
                break;
            } else {
                System.out.println("Wrong email format, try again");
            }
        }

        String password;
        while (true) {
            System.out.println("Password: ");
            password = scanner.nextLine();
            if (password.length() < 8) {
                System.out.println("Your password must contain at least 8 characters");
            } else {
                break;
            }

        }

        return Library.verifyUser(conn, email, password);

    }
}
