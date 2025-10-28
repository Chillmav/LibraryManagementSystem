package classes;

import classes.users.Reader;
import classes.users.User;
import server.Request;
import server.Response;
import utils.EmailService;
import utils.JsonUtils;
import utils.PasswordUtils;
import utils.UserVerification;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                    booksResult.append(JsonUtils.createJsonStringFromObject(book)).append(",");

            }
                if (!hasBooks) {

                    System.out.println("Library doesn't have any books.");
                    return "";
                }

        } catch (SQLException e) {

             e.getStackTrace();

        }
        if (!booksResult.isEmpty()) {
            booksResult.deleteCharAt(booksResult.length() - 1);
        }

        return booksResult.toString();
    }

    public static String displayUserBorrowedBooks(int id, Connection conn) {

        String sql = "SELECT books.title, books.author, books.kind, books.pages, borrowed_books.borrowed_at, books.id, borrowed_books.book_id FROM borrowed_books " +
                "JOIN books ON books.id=borrowed_books.book_id WHERE borrowed_books.user_id=?;";
        StringBuilder booksResult = new StringBuilder();

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
                Book book = new Book(book_title, book_author, false, book_pages, book_kind);
                book.setId(rs.getInt(6));
                booksResult.append(JsonUtils.createJsonStringFromObject(book)).append(",");

            }
            if (!hasBooks) {

                System.out.println("You don`t have borrowed books.");
                return "";
            }

            if (!booksResult.isEmpty()) {
                booksResult.deleteCharAt(booksResult.length() - 1);
            }

            return booksResult.toString();


        } catch (SQLException e) {

            throw new RuntimeException(e);
        }



    }

    public static String createReader(Connection conn, Request request) {

        Pattern emailPattern = Pattern.compile(".+@.+[.][^.]+");
        Pattern name = Pattern.compile("^[A-Z][a-zA-Z]*$");
        var body = request.getBody();

        Map<String, String> data = JsonUtils.createMapFromJson(body);

        try {

            String sql = "INSERT INTO users (firstName, lastName, age, role, email, password_hash, uuid)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            if (!name.matcher(data.get("firstName")).matches()) {
                return Response.registerFailure(request, "Wrong first name");
            }
            if (!name.matcher(data.get("lastName")).matches()) {
                return Response.registerFailure(request, "Wrong last name");
            }
            if (Integer.parseInt(data.get("age")) < 10 || Integer.parseInt(data.get("age")) > 120) {
                return Response.registerFailure(request, "Age must be between 10 and 120");
            }
            if (!emailPattern.matcher(data.get("email")).matches()) {
                return Response.registerFailure(request, "Wrong email");
            }
            if (data.get("password").length() < 8) {
                return Response.registerFailure(request, "Password has to contain at least 8 chars");
            }
            try (PreparedStatement psCheck = conn.prepareStatement("SELECT * FROM users WHERE email = ?")) {

                psCheck.setString(1, data.get("email"));

                var rs = psCheck.executeQuery();

                if (rs.next()) {
                    return Response.registerFailure(request, "Email aready in use");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            ps.setString(1, data.get("firstName"));
            ps.setString(2, data.get("lastName"));
            ps.setInt(3, Integer.parseInt(data.get("age")));
            ps.setString(4, "READER");
            ps.setString(5, data.get("email"));
            ps.setString(6, PasswordUtils.hashPassword(data.get("password")));

            String token = UUID.randomUUID().toString();
            ps.setString(7, token);
            ps.executeUpdate();

            String confirmationLink = "http://localhost:9000/confirm?token=" + token;
            EmailService.sendConfirmationEmail(data.get("email"), confirmationLink);

            return Response.registerSuccess(request, "Success! We sent you an email, in order to confirm your account!");

        } catch (SQLException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }




    }

    public static UserVerification verifyUser(Connection conn, String email, String password) {

        try {

            String sql = "SELECT password_hash, firstName, lastName, role, id, isConfirmed " +
                    "FROM users " +
                    "WHERE email=?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                if (PasswordUtils.verifyPassword(password, rs.getString(1))) {

                    if (!rs.getBoolean(6)) {
                        System.out.println("User`s account isn`t activated");
                        return new UserVerification(new User(rs.getInt(5), Role.valueOf(rs.getString(4)), rs.getString(2), rs.getString(3)), false);
                    }

                    return new UserVerification(new User(rs.getInt(5), Role.valueOf(rs.getString(4)), rs.getString(2), rs.getString(3)), true);
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
