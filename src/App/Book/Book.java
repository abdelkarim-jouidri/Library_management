package App.Book;

public class Book {
    private  String title;
    private  String author;
    private  String ISBN;
    private  int id ;



    //Constructor
    public Book(String title, String author, String ISBN, int id){
        this.id = id;
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
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

//    public int getId(){
//        return this.id;
//    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

}
