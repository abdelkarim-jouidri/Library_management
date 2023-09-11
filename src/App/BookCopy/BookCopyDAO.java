package App.BookCopy;

import App.Book.Book;
import App.DAO;

import java.sql.SQLException;

public interface BookCopyDAO extends DAO<BookCopy> {
    void insertMany(Book book, int times) throws SQLException;
}
