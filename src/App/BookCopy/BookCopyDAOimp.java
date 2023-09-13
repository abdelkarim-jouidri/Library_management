package App.BookCopy;

import App.Book.Book;
import App.Book.BookDAO;
import App.Book.BookDAOImp;
import App.Database.Database;
import App.LibraryMember.LibraryMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookCopyDAOimp implements BookCopyDAO{
    private BookDAOImp bookDAO = new BookDAOImp();
    @Override
    public BookCopy get(int id) throws SQLException {
        BookCopy copy = null;
        Connection con = Database.getConnection();
        String SQL = "SELECT * FROM books_copies WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int copy_ID = rs.getInt("id");
            int book_id = rs.getInt("book_id");
            Book book = bookDAO.fetch(book_id);
            String status = rs.getString("book_status");
            copy = new BookCopy(copy_ID, book, BookCopy.BookState.valueOf(status));
        }
        return copy;
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

    public BookCopy getAvailableCopy (int id) throws SQLException{
        Connection con = Database.getConnection();
        BookCopy copy = null;
        String SQL = "SELECT * FROM books_copies \n" +
                     "WHERE book_id = ? AND book_status = 'AVAILABLE' \n" +
                     "LIMIT 1;" ;
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int ID = rs.getInt("id");
            int bookID = rs.getInt("book_id");
            String status = rs.getString("book_status");
            Book book = new BookDAOImp().get(bookID);
            BookCopy.BookState state = BookCopy.BookState.valueOf(status);
            copy = new BookCopy(ID, book, state);
        }
    return copy;
    }

public int updateStatus(BookCopy copy) throws SQLException{
    int res = 0;
    try {
        Connection connection = Database.getConnection();
        String SQL = "UPDATE books_copies " +
                     "SET book_status = ? " +
                     "WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(SQL);
        ps.setString(1, copy.getState().toString());
        ps.setInt(2, copy.getId());
        res = ps.executeUpdate();
    }catch (Exception e){
        e.printStackTrace();
    }
    return res;
    
}
}
