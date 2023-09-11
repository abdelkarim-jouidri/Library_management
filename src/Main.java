import App.Book.Book;
import App.Book.BookDAO;
import App.Book.BookDAOImp;
import App.BookCopy.BookCopyDAO;
import App.BookCopy.BookCopyDAOimp;
import App.Database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static BookDAO bookDAO = new BookDAOImp();
    private static BookCopyDAO bookCopyDAO = new BookCopyDAOimp();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            int choice;

            do {
                System.out.println("Menu:");
                System.out.println("1. Add a new book to the library");
                System.out.println("2. Show all the available books");
                System.out.println("3. ");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        if(addNewBook() == 1){
                            System.out.println("New Book has been added successfully");
                        }else {
                            System.out.println("Something went wrong");
                        }
                        break;
                    case 2:
                        ArrayList<Book> AllBooks =  bookDAO.getAll();
                        showAllBooks(AllBooks);
                        break;
                    case 3:
                        System.out.println("In development.");
                        break;
                    case 4:
                        System.out.println("Exiting the program.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 4);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showAllBooks (ArrayList<Book> books){
        if(books.isEmpty()){
            System.out.println("No Available Books in the library");
        }else {
            System.out.println("------------------------------------------List of Available books--------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------------------------------");
            System.out.println("| ID         | Title                          | Author                         | ISBN                 |");
            System.out.println("-------------------------------------------------------------------------------------------------------------");

            for (Book book : books) {
                System.out.printf("| %-10d | %-30s | %-30s | %-20s |\n", book.getId(), book.getTitle(), book.getAuthor(), book.getISBN());
            }
        }
    }

    public static int addNewBook(){
        scanner.nextLine();

        while (true){
            System.out.print("Enter the title of the book: ");
            String title = scanner.nextLine().trim();
            if (!Pattern.matches("^[a-zA-Z0-9\\s.,'-]+$", title)){
                System.out.println("Invalid Input For Title.");
                continue;

            }
            System.out.print("Enter the ISBN of the book : ");
            String isbn = scanner.nextLine().trim();
            if(!Pattern.matches("^\\d{13}$", isbn)){
                System.out.println("Invalid ISBN. The ISBN of a book should be a 13-digit number");
                continue;
            }
            System.out.print("Enter the author of the book : ");
            String author = scanner.nextLine().trim();
            if(!Pattern.matches("^[a-zA-Z\\s-]+$", author)){
                System.out.println("Invalid Input For The Author.");
                continue;
            }

            System.out.println("Enter the initial stock number : ");
            int number = scanner.nextInt();

            Book newBook = new Book();
            newBook.setISBN(isbn);
            newBook.setTitle(title);
            newBook.setAuthor(author);
            int res = 1;

            try{
                newBook = bookDAO.insert(newBook);
                bookCopyDAO.insertMany(newBook, number);
            }catch (Exception e){
                e.printStackTrace();
            }
            return res;
        }
    }
}
