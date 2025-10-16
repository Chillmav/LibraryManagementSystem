package classes;

public class Book {

    private int id;
    private String title;
    private String author;
    private boolean available;
    private int pages;
    private Kind kind;


    public Book(String title, String author, boolean available, int pages, Kind kind) {

        this.title = title;
        this.author = author;
        this.available = available;
        this.pages = pages;
        this.kind = kind;

    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", available=" + available +
                ", pages=" + pages +
                ", kind=" + kind +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getPages() {
        return pages;
    }

    public Kind getKind() {
        return kind;
    }

    public int getId() {
        return id;
    }
}
