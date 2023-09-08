import App.Book.Book;
import App.Database.Database;
import App.Library.Library;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

//        Book book1 = new Book("title", "author","ISBN");
//        Book book2 = new Book("title2", "author2","ISBN2");
//        Library library = new Library();
//        library.addBook(book1).markAsBorrowed();
//        library.addBook(book2);
//        Book desiredBook = library.getBook("ISBN");
//        System.out.println("the state of the desired book is : " + desiredBook.getState());
        try {
            Connection connection = Database.getConnection();
            if(connection != null) {
                System.out.println("Successful connection");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }

    }

