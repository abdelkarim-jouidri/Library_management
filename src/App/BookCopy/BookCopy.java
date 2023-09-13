package App.BookCopy;

import App.Book.Book;

public class BookCopy {
    private int id;
    private Book book;
    public BookState state;
    public enum BookState {
        AVAILABLE,
        BORROWED,
        LOST
    }

    public BookCopy() {

    }

    public BookCopy(int id, Book book, BookState state) {
        this.id = id;
        this.book = book;
        this.state = state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookCopy markAsAvailable(){
        this.state = BookState.AVAILABLE;
        return this;
    }

    public BookCopy markAsBorrowed(){
        this.state = BookState.BORROWED;
        return this;
    }

    public BookCopy markAsLost(){
        this.state = BookState.LOST;
        return this;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public String getState() {
        return state.toString();
    }
}
