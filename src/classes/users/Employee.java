package classes.users;

import classes.Book;
import classes.Kind;
import classes.Library;
import classes.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

public class Employee extends User implements classes.interfaces.Employee {

    private final Role role = Role.EMPLOYEE;

    public Employee(int id, Role role, String firstName, String secondName) {
        super(id, role, firstName, secondName);
    }

    @Override
    public void addBook(Connection conn, Scanner scanner) {
        Book book = createBook(scanner);

        try {
            String sql = "INSERT INTO books (title, author, available, pages, kind) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.isAvailable());
            ps.setInt(4, book.getPages());
            ps.setString(5, book.getKind().toString());
            ps.executeUpdate();

            System.out.println("Book added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void removeBook(Connection conn, int bookId) {

        String sqlIdCheck = "SELECT * FROM books WHERE id=?";
        boolean bookExist = false;
        try (PreparedStatement ps = conn.prepareStatement(sqlIdCheck)) {

            ps.setInt(1, bookId);

            var rs = ps.executeQuery();

            if (rs.next()) {
                bookExist = true;
            } else {
                System.out.println("You`ve chosen book that doesn't exist.");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sql = "DELETE FROM books WHERE id=? AND available=1;";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);

            int rs = ps.executeUpdate();

            if (rs != 0) {
                System.out.println("Book successfully deleted from library.");
            } else {
                if (bookExist) {
                    System.out.println("You cannot delete book that remains borrowed.");

                }
            }
        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    @Override
    public void editBook(Connection conn, int bookId) {

    }


    public Book createBook(Scanner scanner) {

        boolean validTitle = false;
        boolean validAuthor = false;
        boolean validPages = false;

        String title = "";
        String author = "";
        int pages = 0;

        while (!validTitle) {
            System.out.println("Book Title: ");
            title = scanner.nextLine();
            if (title.isEmpty()) {
                System.out.println("Invalid book title");
            } else {
                validTitle = true;
            }
        }

        while (!validAuthor) {
            System.out.println("Book Author: ");
            author = scanner.nextLine();
            if (author.isEmpty()) {
                System.out.println("Invalid book author");
            } else {
                validAuthor = true;
            }
        }

        while (!validPages) {
            System.out.println("Number of pages: ");
            pages = scanner.nextInt();
            scanner.nextLine();
            if (pages <= 0) {
                System.out.println("Invalid number of pages");
            } else {
                validPages = true;
            }
        }

        while (true) {
            System.out.println("Choose book kind from list below: ");
            Arrays.stream(Kind.values()).forEach(kind1 -> {
                System.out.printf(" %s", kind1.toString());
            });
            System.out.println();
            String kind = scanner.next();
            for (var kind1 : Kind.values()) {
                if (kind.equalsIgnoreCase(kind1.toString())) {
                    return new Book(title, author, true, pages, kind1);
                }
            }
            System.out.println("Invalid kind of book");
        }


    }

}
