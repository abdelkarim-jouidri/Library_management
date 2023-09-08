package App.Book;

import App.Database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAOImp implements BookDAO{

    @Override
    public Book get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Book book = null;
        String SQL = "SELECT * FROM books WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int bookID = rs.getInt("id");
            String ISBN = rs.getString("ISBN");
            String title = rs.getString("title");
            String author = rs.getString("author");

            book = new Book(title, author, ISBN, bookID);

        }
        return book;
    }

    @Override
    public ArrayList<Book> getAll() throws SQLException {
        return null;
    }

    @Override
    public int save(Book instance) throws SQLException {
        return 0;
    }

    @Override
    public int insert(Book instance) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "INSERT INTO books(title, author, ISBN) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setString(1, instance.getTitle());
        ps.setString(2, instance.getAuthor());
        ps.setString(3, instance.getISBN());

        int result = ps.executeUpdate();
        return result;
    }

    @Override
    public int update(Book instance) throws SQLException {
        return 0;
    }

    @Override
    public int delete(Book instance) throws SQLException {
        return 0;
    }
}
