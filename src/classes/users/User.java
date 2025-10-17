package classes.users;


import classes.Library;
import classes.Role;

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

    public int displayPanel(Scanner scanner, Library library, Connection conn) {

        System.out.println("Display all books(1)");
        System.out.println("Display your borrowed books(2)");
        System.out.println("Borrow book(3)");
        System.out.println("Give back the book(4)");
        System.out.println("Log out(5)");

        String option = scanner.nextLine();

        switch (option) {

            case "1":
                library.displayBooks(conn);
                return 1;

            case "2":
                library.displayUserBorrowedBooks(this.id, conn);
                return 2;

            case "3":

                borrowBook(scanner, conn, library);
                return 3;

            case "4":
//                returnBook(scanner, conn, library);
                return 4;

            case "5":
                return 5;

            default:
                System.out.println("Wrong number provided");
                return 1;
        }

    }

//    private void returnBook(Scanner scanner, Connection conn, Library library) {
//
//        boolean hasBooks = library.displayUserBorrowedBooks(this.id, conn);
//
//        if (!hasBooks) {
//            return;
//        }
//
//        System.out.println();
//        System.out.println("Type book id: ");
//        String idString = scanner.nextLine();
//        int id = Integer.parseInt(idString);
//
//        String sql = "SELECT book_id from borrowed_books WHERE book_id=?";
//
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//
//            ps.setInt(1, id);
//
//            var rs = ps.executeQuery();
//
//            boolean isBookExist = false;
//
//            if (rs.next()) {
//                isBookExist = true;
//
//                String deleteSql = "DELETE FROM borrowed_books WHERE book_id=?";
//
//                        try (PreparedStatement ps2 = conn.prepareStatement(deleteSql)) {
//                            ps2.setInt(1, id);
//                            ps2.executeUpdate();
//                        } catch (SQLException e) {
//                            e.getStackTrace();
//                        }
//
//                String setAvailableTrue = "UPDATE books SET available=true WHERE id=?";
//
//                        try (PreparedStatement ps3 = conn.prepareStatement(setAvailableTrue)) {
//                            ps3.setInt(1, id);
//                            ps3.executeUpdate();
//                            System.out.println("You returned your book successfully");
//                        }
//
//
//
//            }
//
//            if (!isBookExist) {
//                System.out.println("Please provide correct id.");
//            }
//
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


    private void borrowBook(Scanner scanner, Connection conn, Library library) {

        library.displayBooks(conn);
        System.out.println();
        System.out.println("Type book id: ");
        String idString = scanner.nextLine();
        int id = Integer.parseInt(idString);

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

                }
            }

            if (!isBookExist) {
                System.out.println("There is no book in library with such an index.");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
