import App.Book.Book;
import App.Book.BookDAOImp;
import App.BookCopy.BookCopy;
import App.BookCopy.BookCopyDAOimp;
import App.BorrowingRecord.BorrowingRecord;
import App.BorrowingRecord.BorrowingRecordDAOimp;
import App.LibraryMember.LibraryMember;
import App.LibraryMember.LibraryMemberDAOimp;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    private static BookDAOImp bookDAO = new BookDAOImp();
    private static BookCopyDAOimp bookCopyDAO = new BookCopyDAOimp();
    private static LibraryMemberDAOimp libraryMemberDAO = new LibraryMemberDAOimp();
    private static BorrowingRecordDAOimp borrowingRecordDAO = new BorrowingRecordDAOimp();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            int choice;

            do {
                System.out.println("Menu:");
                System.out.println("1. Add a new book to the library\t 4. Manage the stock");
                System.out.println("2. Show all the available books\t\t 5. Add new members");
                System.out.println("3. Manage the collection of books\t 6. Manage borrowing a book");
                System.out.println("7. Search for a book\t");
                System.out.println("8. Exit");


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
                        manageBorrowingRecord();
                        break;
                    case 7:
                        searchForBooks();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 8);


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
                    Book book = bookDAO.getBorrowedBook(bookID);
                    if (book == null) {
                        System.out.println("No existing book with the ID = " + bookID);
                        continue;
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

    public static void manageBorrowingRecord() {
        try {
            while (true) {
                System.out.println("1. Register a book borrowing record");
                System.out.println("2. Register a book return");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println("Enter the ID of the book to be borrowed: ");
                    int bookID = scanner.nextInt();
                    Book book = bookDAO.get(bookID);

                    if (book == null) {
                        System.out.println("No Available copies for the book with the ID = " + bookID);
                        continue;
                    }
                    System.out.println("Enter the ID of the member to register the borrowing for : ");
                    int memberID = scanner.nextInt();
                    LibraryMember member = libraryMemberDAO.get(memberID);

                    if (member == null) {
                        System.out.println("None existing member with the ID = " + memberID);
                        continue;
                    }
                    BorrowingRecord record = new BorrowingRecord();
                    BookCopy copy = bookCopyDAO.getAvailableCopy(bookID);
                    record.setBookCopy(copy);
                    record.setLibraryMember(member);
                    record.setBorrowDate();

                    borrowingRecordDAO.insert(record);
                    copy.markAsBorrowed();
                    bookCopyDAO.updateStatus(copy);


                    System.out.println("Registering a book borrowing...");
                } else if (choice == 2) {
                    System.out.println("Enter the ID of the book to be borrowed: ");
                    int bookID = scanner.nextInt();
                    Book book = bookDAO.getBorrowedBook(bookID);

                    if (book == null) {
                        System.out.println("No Available copies for the book with the ID = " + bookID);
                        continue;
                    }
                    System.out.println("Enter the ID of the member to register the return of the book for : ");
                    int memberID = scanner.nextInt();
                    LibraryMember member = libraryMemberDAO.get(memberID);

                    if (member == null) {
                        System.out.println("None existing member with the ID = " + memberID);
                        continue;
                    }

                    BorrowingRecord record = borrowingRecordDAO.get(bookID, memberID);

                    if(record == null){
                        System.out.println("There is no record for this book and this member");
                        continue;
                    }

                    int status = borrowingRecordDAO.update(record);

                    if (status == 1) {

                        System.out.println("The return of the book has been successfully registered");
                    }
                    else {
                        System.out.println("Something went wrong");
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


    public static void searchForBooks(){
        int choice;
        try {
            while (true) {

                System.out.println("1. Search by Title");
                System.out.println("2. Search by Author");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (choice == 1) {
                    System.out.print("Enter the title: ");
                    String title = scanner.nextLine().trim();

                    if (Pattern.matches("^[a-zA-Z0-9\\s.,'-]+$", title)) {
                        ArrayList<Book> searchResults = bookDAO.searchBooksByTitle(title);
                        System.out.println("inside the if clause");
                        System.out.println("Search Results:");
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("| ID  | Title                        | Author                     | Quantity |");
                        System.out.println("--------------------------------------------------------------");

                        for (Book book : searchResults) {
                            System.out.printf("| %-3d | %-28s | %-26s | %-8d |\n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity());
                        }

                        System.out.println("--------------------------------------------------------------");

                        continue; // Return to the menu
                    }

                    // Implement the search by title logic here
                    System.out.println("Searching by Title: " + title);
                } else if (choice == 2) {
                    System.out.print("Enter the author: ");
                    String author = scanner.nextLine().trim();

                    if (Pattern.matches("^[a-zA-Z\\s-]+$", author)) {
                        ArrayList<Book> searchResults = bookDAO.searchBooksByAuthor(author);
                        System.out.println("inside the if clause");
                        System.out.println("Search Results:");
                        System.out.println("--------------------------------------------------------------");
                        System.out.println("| ID  | Title                        | Author                     | Quantity |");
                        System.out.println("--------------------------------------------------------------");

                        for (Book book : searchResults) {
                            System.out.printf("| %-3d | %-28s | %-26s | %-8d |\n", book.getId(), book.getTitle(), book.getAuthor(), book.getQuantity());
                        }

                        System.out.println("--------------------------------------------------------------");
                        continue; // Return to the menu
                    }

                    // Implement the search by author logic here
                    System.out.println("Searching by Author: " + author);
                } else if (choice == 3) {
                    System.out.println("Exiting the menu.");
                    scanner.close();
                    return; // Exit the program
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}
