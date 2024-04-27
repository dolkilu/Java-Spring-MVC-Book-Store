package hkmu.comps380f.s1326557_project.model;

public class OrderBookDTO {
    private Book book;
    private int quantity;

    public OrderBookDTO(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
