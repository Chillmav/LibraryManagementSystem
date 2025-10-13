package classes;

import classes.users.Reader;
import classes.users.User;
import utils.PasswordUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private final String name;


    public Library(String name) {
        this.name = name;
    }


    public static String displayBooks(Connection conn) {

        StringBuilder booksResult = new StringBuilder();

        try {

            Statement st = conn.createStatement();

                String sql = "select * from books";
                var rs = st.executeQuery(sql);
                boolean hasBooks = false;

                while (rs.next()) {

                    if (!hasBooks) {
                        System.out.println("Library books: ");
                        hasBooks = true;
                    }
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    boolean avai = rs.getBoolean("available");
                    int pages = rs.getInt("pages");
                    Kind kind = Kind.valueOf(rs.getString("kind").toUpperCase());

                    Book book = new Book(title, author, avai, pages, kind);
                    book.setId(id);

                    booksResult.append(book).append("\n");




            }
                if (!hasBooks) {

                    System.out.println("Library doesn't have any books.");
                    return "";
                }

        } catch (SQLException e) {

             e.getStackTrace();

        }
        System.out.println(booksResult);
        return booksResult.toString();
    }

    public static boolean displayUserBorrowedBooks(int id, Connection conn) {

        String sql = "SELECT books.title, books.author, books.kind, books.pages, borrowed_books.borrowed_at, books.id, borrowed_books.book_id FROM borrowed_books " +
                "JOIN books ON books.id=borrowed_books.book_id WHERE borrowed_books.user_id=?;";
        try {

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            boolean hasBooks = false;

            while (rs.next()) {

                if (!hasBooks) {
                    System.out.println("Your borrowed books: ");
                    hasBooks = true;
                }
                String book_title = rs.getString(1);
                String book_author = rs.getString(2);
                Kind book_kind = Kind.valueOf(rs.getString(3));
                int book_pages = rs.getInt(4);
                Timestamp borrowed_at = rs.getTimestamp(5);
                Book book = new Book(book_title, book_author, false, book_pages, book_kind);
                book.setId(rs.getInt(6));
                System.out.println(book + " borrowed at: " + borrowed_at.toLocalDateTime().toString());
            }
            if (!hasBooks) {
                System.out.println("You don`t have borrowed books.");
                return false;
            }
            return true;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    public static void createReader(Connection conn, String firstName, String lastName, int age, String email, String password) {

        try {
            String sql = "INSERT INTO users (firstName, lastName, age, role, email, password_hash)" +
                    "VALUES (?, ?, ?, ?, ?,  ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.setString(4, "READER");
            ps.setString(5, email);
            ps.setString(6, PasswordUtils.hashPassword(password));
            ps.executeUpdate();
        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }


    }

    public static User verifyUser(Connection conn, String email, String password) {

        try {

            String sql = "SELECT password_hash, firstName, lastName, role, id " +
                    "FROM users " +
                    "WHERE email=?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (PasswordUtils.verifyPassword(password, rs.getString(1))) {
                    return new User(rs.getInt(5), Role.valueOf(rs.getString(4)), rs.getString(2), rs.getString(3));
                } else {
                    System.out.println("Password doesn't match");
                    return null;
                }
            } else {
                System.out.println("User with that email doesn't exist");
                return null;
            }


        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }

    }




}
