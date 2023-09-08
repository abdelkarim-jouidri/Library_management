import App.Book.Book;
import App.Book.BookDAO;
import App.Book.BookDAOImp;
import App.Database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            BookDAO bookDAO = new BookDAOImp();
            Book book = new Book();
            book.setISBN("34254");
            book.setTitle("Brothers Karamazov");
            book.setAuthor("Fyodor Dostoevsky");
            bookDAO.insert(book);
        }catch (Exception e){
            e.printStackTrace();
        }

        }

    }

