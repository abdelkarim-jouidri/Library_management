package App.Book;

import App.Database.Database;

import java.sql.*;
import java.util.ArrayList;

public class BookDAOImp implements BookDAO{

    @Override
    public Book get(int id) throws SQLException {
        Connection con = Database.getConnection();
        Book book = null;
        String SQL = "SELECT books.id, books.ISBN, books.author, books.title, COUNT(*) AS copy_count\n" +
                "FROM books \n" +
                "INNER JOIN books_copies \n" +
                "ON books.id = books_copies.book_id\n" +
                "WHERE books_copies.book_status = 'AVAILABLE' AND books.id = ?\n"  +
                "GROUP BY books.id;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int bookID = rs.getInt("id");
            String ISBN = rs.getString("ISBN");
            String title = rs.getString("title");
            String author = rs.getString("author");
            int quantity = rs.getInt("copy_count");

            book = new Book(title, author, ISBN, bookID, quantity);

        }
        return book;
    }

    @Override
    public ArrayList<Book> getAll() throws SQLException {
        Connection con = Database.getConnection();
        ArrayList<Book> books = new ArrayList<>();
        String SQL = "SELECT * FROM books";
        PreparedStatement ps = con.prepareStatement(SQL);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int id = rs.getInt("id");
            System.out.println("FROM get all method : id = "+ id);
            Book book = get(id);
            books.add(book);
        }

        return books;
    }

    @Override
    public int save(Book instance) throws SQLException {
        return 0;
    }

    @Override
    public Book insert(Book instance) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "INSERT INTO books(title, author, ISBN) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, instance.getTitle());
        ps.setString(2, instance.getAuthor());
        ps.setString(3, instance.getISBN());
        int result = ps.executeUpdate();
        if (result == 1){
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()){
                int generatedID = generatedKeys.getInt(1);
                instance.setId(generatedID);
            }
        }
        ps.close();
        con.close();
        return instance;
    }

    @Override
    public int update(Book book) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "UPDATE books SET title = ? , author = ? , ISBN = ? WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setString(1, book.getTitle());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getISBN());
        ps.setInt(4, book.getId());
        return 0;
    }

    @Override
    public int delete(Book book) throws SQLException {
        Connection con = Database.getConnection();
        String SQL = "DELETE FROM books WHERE id = ?";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1,book.getId());
        int result = ps.executeUpdate();

        return result;
    }
}
