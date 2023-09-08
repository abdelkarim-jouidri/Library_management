package App.BookCopy;

import App.Book.Book;

public class BookCopy {
    private Book book;
    public BookState state;
    public enum BookState {
        AVAILABLE,
        BORROWED,
        LOST
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
}
