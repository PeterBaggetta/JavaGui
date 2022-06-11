package eStoreSearch;

public class Books extends Product {

    //varibles for book class only
    private String author;
    private String publisher;

    //setter
    public Books (String id, String description, String price, String year, String a, String p) {
        super(id, description, price, year);
        author = a;
        publisher = p;
    }

    //getter
    public String getAuthor() {
        return author;
    }
    public String getPublisher() {
        return publisher;
    }

    //print out the book
    @Override
    public String toString() {
        return("Type = Book\n" + super.toString() + "Author = " + author + "\nPublisher = " + publisher + "\n");
    }

    //print out the book to the output file
    @Override
    public String printOutput() {
        return("Type = \"book\"\n" + super.printOutput() + "Author = \"" + author + "\"\nPublisher = \"" + publisher + "\"\n");
    }
}