package App.BookCopy;

import App.Book.Book;
import App.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookCopyDAOimp implements BookCopyDAO{
    @Override
    public BookCopy get(int id) throws SQLException {
        return null;
    }

    @Override
    public ArrayList<BookCopy> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(BookCopy instance) throws SQLException {
        return 0;
    }

    @Override
    public BookCopy insert(BookCopy instance) throws SQLException {
        BookCopy newBookCopy = new BookCopy();
        return newBookCopy;
    }

    @Override
    public int insertMany(Book book, int times) throws SQLException {
        int successfulInsertions = 0;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO books_copies (book_id, book_status) VALUES (?, 'AVAILABLE')")) {

            ps.setInt(1, book.getId());

            for (int number = 1; number <= times; number++) {
                ps.addBatch();
            }

            int[] batchResults = ps.executeBatch();

            // Count the number of successful insertions
            for (int result : batchResults) {
                if (result != PreparedStatement.EXECUTE_FAILED) {
                    successfulInsertions++;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while inserting copies.");
        }

        return successfulInsertions;
    }


    @Override
    public int update(BookCopy instance) throws SQLException {
        return 0;
    }

    @Override
    public int delete(BookCopy instance) throws SQLException {
        return 0;
    }

    public int deleteMany( int times) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "DELETE FROM books_copies WHERE book_status = 'AVAILABLE' LIMIT ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, times);
        int result = ps.executeUpdate();

        return  result;
    }
}
