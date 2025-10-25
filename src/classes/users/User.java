package classes.users;


import classes.Library;
import classes.Role;
import server.Request;
import server.Response;
import utils.JsonUtils;

import java.lang.module.ResolutionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;

public class User {

    private final int id;
    private final Role role;
    private final String firstName;
    private final String secondName;

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public User(int id, Role role, String firstName, String secondName) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String returnBook(Connection conn, Request request) {

        System.out.println(request.getBody());
        var book = JsonUtils.createMapFromJson(request.getBody());
        int id = Integer.parseInt(book.get("bookId"));

        String sql = "SELECT book_id from borrowed_books WHERE book_id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            var rs = ps.executeQuery();

            boolean isBookExist = false;

            if (rs.next()) {

                isBookExist = true;
                String deleteSql = "DELETE FROM borrowed_books WHERE book_id=?";

                        try (PreparedStatement ps2 = conn.prepareStatement(deleteSql)) {
                            ps2.setInt(1, id);
                            ps2.executeUpdate();
                        } catch (SQLException e) {
                            e.getStackTrace();
                        }

                String setAvailableTrue = "UPDATE books SET available=true WHERE id=?";

                        try (PreparedStatement ps3 = conn.prepareStatement(setAvailableTrue)) {
                            ps3.setInt(1, id);
                            ps3.executeUpdate();
                            System.out.println("You returned your book successfully");
                            return Response.success(request);
                        }



            }

            if (!isBookExist) {

                System.out.println("Please provide correct id.");
                return Response.failure(request);

            }


        } catch (SQLException e) {
            Response.failure(request);
            throw new RuntimeException(e);
        }

        return Response.failure(request);

    }


    public String borrowBook(Connection conn, Request request) {

        System.out.println(request.getBody());
        var book = JsonUtils.createMapFromJson(request.getBody());
        int id = Integer.parseInt(book.get("bookId"));
        System.out.println(id);
        String sql = "SELECT available, title from books WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            var rs = ps.executeQuery();

            boolean isBookExist = false;

            if (rs.next()) {


                isBookExist = true;

                if (rs.getBoolean(1)) {

                    String setAvailableToFalse = "UPDATE books " +
                            "SET available=false " +
                            "WHERE id=?";

                    PreparedStatement ps2 = conn.prepareStatement(setAvailableToFalse);

                    ps2.setInt(1, id);

                    ps2.executeUpdate();

                    String addToBorrowedBooks = "INSERT INTO borrowed_books (user_id, book_id, borrowed_at) VALUES (?, ?, CURRENT_TIMESTAMP)";

                    PreparedStatement ps3 = conn.prepareStatement(addToBorrowedBooks);

                    ps3.setInt(1, this.id);
                    ps3.setInt(2, id);

                    ps3.executeUpdate();

                    System.out.printf(
                            "You borrowed  \"%s\"  successfully \n"
                            , rs.getString(2));


                    return Response.success(request);
                }

            }

//            if (!isBookExist) {
//                System.out.println("There is no book in library with such an index.");
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Response.failure(request);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
