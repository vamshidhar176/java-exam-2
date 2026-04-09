import java.util.*;

public class Library {
    private ArrayList<Book> books = new ArrayList<>();

    public Library() {
        books.add(new Book(1, "Java", "Gosling", true));
        books.add(new Book(2, "OOP", "Bjarne", true));
        books.add(new Book(3, "DSA", "Weiss", true));
        books.add(new Book(4, "OS", "Galvin", true));
        books.add(new Book(5, "DBMS", "Korth", true));
    }

    public void addBook(Book b) {
        books.add(b);
    }

    public void issueBook(int id) throws LibraryException {
        Book b = findBook(id);
        if (!b.isAvailable()) throw new LibraryException("Already issued");
        b.setAvailable(false);
    }

    public void returnBook(int id) throws LibraryException {
        Book b = findBook(id);
        if (b.isAvailable()) throw new LibraryException("Invalid return");
        b.setAvailable(true);
    }

    public void displayAvailableBooks() {
        for (Book b : books) {
            if (b.isAvailable()) System.out.println(b);
        }
    }

    private Book findBook(int id) throws LibraryException {
        for (Book b : books) {
            if (b.getBookId() == id) return b;
        }
        throw new LibraryException("Book not found");
    }
}