import java.util.*;

// Class to get all the details of book
abstract class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(int bookId, String title, String author) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive.");
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true; // Default state
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void borrowBook() {
        if (!isAvailable) {
            System.out.println("Book is currently unavailable.");
        } else {
            isAvailable = false;
            System.out.println("Book borrowed successfully.");
        }
    }

    public void returnBook() {
        if (isAvailable) {
            System.out.println("Book was not borrowed.");
        } else {
            isAvailable = true;
            System.out.println("Book returned successfully.");
        }
    }

    public abstract void displayBookInfo();

    public double calculateFine(int daysLate) {
        return daysLate * 2.0;
    }
}

// class for those who are borrowing Reference Books
class ReferenceBook extends Book {
    private int edition;

    public ReferenceBook(int bookId, String title, String author, int edition) {
        super(bookId, title, author);
        if (edition <= 0) {
            throw new IllegalArgumentException("Edition must be positive.");
        }
        this.edition = edition;
    }

    public int getEdition() {
        return edition;
    }

    @Override
    public void displayBookInfo() {
        System.out.println("Reference Book - ID: " + getBookId() +
                ", Title: " + getTitle() +
                ", Author: " + getAuthor() +
                ", Edition: " + edition +
                ", Available: " + isAvailable());
    }
}

// Class for those who are borrowing Fictional Books
class FictionBook extends Book {
    private String genre;

    public FictionBook(int bookId, String title, String author, String genre) {
        super(bookId, title, author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public void displayBookInfo() {
        System.out.println("Fiction Book - ID: " + getBookId() +
                ", Title: " + getTitle() +
                ", Author: " + getAuthor() +
                ", Genre: " + genre +
                ", Available: " + isAvailable());
    }
}

// class to check for availability of books
class Periodical extends ReferenceBook {
    private String issueFrequency;

    public Periodical(int bookId, String title, String author, int edition, String issueFrequency) {
        super(bookId, title, author, edition);
        this.issueFrequency = issueFrequency;
    }

    public String getIssueFrequency() {
        return issueFrequency;
    }

    @Override
    public void displayBookInfo() {
        System.out.println("Periodical - ID: " + getBookId() +
                ", Title: " + getTitle() +
                ", Author: " + getAuthor() +
                ", Edition: " + getEdition() +
                ", Issue Frequency: " + issueFrequency +
                ", Available: " + isAvailable());
    }
}

// Main class for library management system
public class LibraryManagementSystem {
    private List<Book> books = new ArrayList<>();
    private int totalBooks = 0;
    private int borrowedBooks = 0;

    public void addBook(Book book) {
        books.add(book);
        totalBooks++;
    }

    public void borrowBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isAvailable()) {
                    book.borrowBook();
                    borrowedBooks++;
                } else {
                    System.out.println("Book is unavailable.");
                }
                return;
            }
        }
        System.out.println("Book with ID " + bookId + " not found.");
    }

    public void returnBook(int bookId) {
        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (!book.isAvailable()) {
                    book.returnBook();
                    borrowedBooks--;
                } else {
                    System.out.println("Book was not borrowed.");
                }
                return;
            }
        }
        System.out.println("Book with ID " + bookId + " not found.");
    }

    public void displayAllBooks() {
        System.out.println("\nLibrary Books:");
        for (Book book : books) {
            book.displayBookInfo();
        }
    }

    public void displayLibraryOverview() {
        System.out.println("\nLibrary Overview:");
        System.out.println("Total Books: " + totalBooks);
        System.out.println("Books Borrowed: " + borrowedBooks);
        System.out.println("Books Available: " + (totalBooks - borrowedBooks));
    }

    // main method to validate input from the user
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        library.addBook(new ReferenceBook(101, "Java Programming", "James Gosling", 5));
        library.addBook(new FictionBook(102, "The Hobbit", "J.R.R. Tolkien", "Fantasy"));
        library.addBook(new Periodical(103, "AI Journal", "John Doe", 3, "Monthly"));

        library.displayAllBooks();

        library.borrowBook(101);
        library.borrowBook(101);

        library.returnBook(101);
        library.returnBook(101);

        library.displayLibraryOverview();
    }
}
