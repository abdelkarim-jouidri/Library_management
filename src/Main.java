import App.Book.Book;
import App.Book.BookDAO;
import App.Book.BookDAOImp;
import App.BookCopy.BookCopyDAO;
import App.BookCopy.BookCopyDAOimp;
import App.Database.Database;
import App.LibraryMember.LibraryMember;
import App.LibraryMember.LibraryMemberDAOimp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static BookDAO bookDAO = new BookDAOImp();
    private static BookCopyDAOimp bookCopyDAO = new BookCopyDAOimp();
    private static LibraryMemberDAOimp libraryMemberDAO = new LibraryMemberDAOimp();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            int choice;

            do {
                System.out.println("Menu:");
                System.out.println("1. Add a new book to the library\t 4. Manage the stock");
                System.out.println("2. Show all the available books\t\t 5. Add new members");
                System.out.println("3. Manage the collection of books\t 6. Manage borrowing a book");
                System.out.println("7. Exit");


                choice = scanner.nextInt();
                scanner.nextLine();

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
                        System.out.println("management of books");
                        break;
                    case 4:
                        manageTheStock();
                        break;
                    case 5:
                        addNewMember();
                        break;
                    case 6:
                        System.out.println("managing borrowing record.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 7);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void showAllBooks (ArrayList<Book> books){
        if(books.isEmpty()){
            System.out.println("No Available Books in the library");
        }else {
            System.out.println("------------------------------------------List of Available books--------------------------------------------------------");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");
            System.out.println("| ID         | Title                          | Author                         | ISBN                 | Quantity        |");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------");

            for (Book book : books) {
                System.out.printf("| %-10d | %-30s | %-30s | %-20s | %-15s |\n", book.getId(), book.getTitle(), book.getAuthor(), book.getISBN(), book.getQuantity());
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

    public static void manageTheStock() {
        try {
            while (true) {
                System.out.println("1. Add new copies to the book collection");
                System.out.println("2. Remove copies from the book collection");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println("Enter the ID of the book: ");
                    // this still needs input validation
                    int bookID = scanner.nextInt();
                    Book book = bookDAO.get(bookID);

                    if (book == null) {
                        System.out.println("No existing book with the ID = " + bookID);
                        continue; // Continue the loop to display the menu again
                    }

                    System.out.println("Enter the number of copies: ");
                    int number = scanner.nextInt();

                    int successfulInsertions = bookCopyDAO.insertMany(book, number);
                    if (successfulInsertions == number) {
                        System.out.println("The operation has been successful");
                    } else {
                        System.out.println("Failed to add copies.");
                    }
                } else if (choice == 2) {
                    System.out.println("Enter the ID of the book: ");
                    int bookID = scanner.nextInt();
                    Book book = bookDAO.get(bookID);

                    if (book == null) {
                        System.out.println("No existing book with the ID = " + bookID);
                        continue; // Continue the loop to display the menu again
                    }

                    System.out.println("Enter the number of copies: ");
                    int number = scanner.nextInt();

                    int result = bookCopyDAO.deleteMany(number);
                    if (result == 1) {
                        System.out.println("The operation has been successful");
                    } else {
                        System.out.println("Failed to remove copies.");
                    }
                } else if (choice == 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong!!");
        }
    }

    public static void addNewMember() {
        try {
            while (true) {
                System.out.print("Enter the member's first name: ");
                String firstName = scanner.nextLine();

                System.out.print("Enter the member's last name: ");
                String lastName = scanner.nextLine();

                // input validation
                boolean isFirstNameValid = Pattern.matches("^[A-Za-z ]+$", firstName);
                boolean isLastNameValid = Pattern.matches("^[A-Za-z ]+$", lastName);

                if (!isFirstNameValid || !isLastNameValid) {
                    System.out.println("Invalid first or last name. Names should contain only letters and spaces.");
                    continue;
                }

                LibraryMember member = new LibraryMember();
                member.setFirstName(firstName);
                member.setLastName(lastName);

                LibraryMember result = libraryMemberDAO.insert(member);

                if (result != null) {
                    System.out.println("Member added successfully.");
                } else {
                    System.out.println("Failed to add the member.");
                }

                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Something went wrong while adding the member.");
        }
    }




}
