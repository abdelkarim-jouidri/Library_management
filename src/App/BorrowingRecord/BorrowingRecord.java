package App.BorrowingRecord;

import App.BookCopy.BookCopy;
import App.LibraryMember.LibraryMember;

import java.sql.Date;
import java.time.LocalDate;

public class BorrowingRecord {
    private int id;
    private BookCopy bookCopy;
    private LibraryMember libraryMember;
    private Date borrowDate;
    private Date returnDate;

    public BorrowingRecord(){

    };

    public BorrowingRecord(int id, BookCopy bookCopy, LibraryMember libraryMember, Date borrowDate, Date returnDate) {
        this.id = id;
        this.bookCopy = bookCopy;
        this.libraryMember = libraryMember;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public Date getBorrowDate() {
        return  borrowDate;
    }

    public Date getReturnDate() {
        return  returnDate;
    }

    public void setBookCopy(BookCopy bookCopy) {
        this.bookCopy = bookCopy;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public void setBorrowDate() {
        this.borrowDate = Date.valueOf(LocalDate.now());
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BorrowingRecord{" +
                "id=" + id +
                ", bookCopy=" + bookCopy +
                ", libraryMember=" + libraryMember +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
