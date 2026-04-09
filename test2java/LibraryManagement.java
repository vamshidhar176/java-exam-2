import java.util.*;

public class LibraryManagement {
    public static void main(String[] args) {
        Library lib = new Library();
        Scanner sc = new Scanner(System.in);

        try {
            lib.displayAvailableBooks();
            lib.issueBook(1);
            lib.returnBook(1);
        } catch (LibraryException e) {
            System.out.println(e.getMessage());
        }
    }
}