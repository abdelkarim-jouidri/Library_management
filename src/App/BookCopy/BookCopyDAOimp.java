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
    public void insertMany(Book book , int times) throws SQLException{
        Connection con = Database.getConnection();
        String SQL = "INSERT INTO books_copies (book_id, book_status) VALUES (? , ?)";
        PreparedStatement ps = con.prepareStatement(SQL);
        for (int number = 1 ; number <= times ; number++){
            ps.setInt(1, book.getId());
            ps.setString(2,"AVAILABLE");
            ps.addBatch();
        }
        ps.executeBatch();
        System.out.println("The id of the book is : "+ book.getId());

    }

    @Override
    public int update(BookCopy instance) throws SQLException {
        return 0;
    }

    @Override
    public int delete(BookCopy instance) throws SQLException {
        return 0;
    }
}
