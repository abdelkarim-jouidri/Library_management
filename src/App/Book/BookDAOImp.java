package App.Book;

import App.Database.Database;

import java.sql.*;
import java.util.ArrayList;

public class BookDAOImp implements BookDAO{

    public Book fetch(int id) throws SQLException{
        Connection con = Database.getConnection();
        Book book = null;
        String SQL = "SELECT * FROM books WHERE id = ?;";
        PreparedStatement ps = con.prepareStatement(SQL);
        ps.setInt(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()){
            int bookID = rs.getInt("id");
            String ISBN = rs.getString("ISBN");
            String title = rs.getString("title");
            String author = rs.getString("author");

            book = new Book();
            book.setId(bookID);
            book.setTitle(title);
            book.setISBN(ISBN);
            book.setAuthor(author);

        }
        return book;
    }

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

    public Book getBorrowedBook(int id) throws SQLException {
        Connection con = Database.getConnection();
        Book book = null;
        String SQL = "SELECT books.id, books.ISBN, books.author, books.title, COUNT(*) AS copy_count\n" +
                "FROM books \n" +
                "INNER JOIN books_copies \n" +
                "ON books.id = books_copies.book_id\n" +
                "WHERE books_copies.book_status = 'Borrowed' AND books.id = ?\n"  +
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

    public ArrayList<Book> searchBooksByAuthorOrTitle(String author, String title) throws SQLException{
        ArrayList<Book> books = new ArrayList<>();
        Connection connection = Database.getConnection();
        String SQL = "SELECT b.id, b.title, b.ISBN, b.author, COUNT(*) AS quantity FROM books b\n" +
                "INNER JOIN books_copies copy ON copy.book_id = b.id\n" +
                "WHERE title LIKE ? OR author LIKE ?\n" +
                "GROUP BY b.id;\n";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, "%" + title + "%"); // Search for authors containing the provided text
            preparedStatement.setString(2, "%" + author + "%");   // Search for titles containing the provided text
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println(preparedStatement.toString());
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String ISBN = resultSet.getString("ISBN");
                String bookAuthor = resultSet.getString("author");
                String bookTitle = resultSet.getString("title");
                int quantity = resultSet.getInt("quantity");

                Book book = new Book(bookTitle, bookAuthor, ISBN, id, quantity);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) throws SQLException{

        return searchBooksByAuthorOrTitle(author, " ");
    }

    public ArrayList<Book> searchBooksByTitle(String title) throws SQLException{

        return searchBooksByAuthorOrTitle(" ", title);
    }

}
