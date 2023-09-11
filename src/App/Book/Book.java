package App.Book;

public class Book {
    private  String title;
    private  String author;
    private  String ISBN;
    private  int id ;

    private int quantity;



    //Constructor
    public Book(String title, String author, String ISBN, int id, int quantity){
        this.id = id;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.quantity = quantity;
    }

    public Book(){
        this.title = null;
        this.author = null;
        this.ISBN = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle(){
        return this.title;
    }
    public String getAuthor(){
        return this.author;
    }
    public String getISBN(){
        return this.ISBN;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
