package hkmu.comps380f.s1326557_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "authors", nullable = false)
    @NotBlank
    private String authors;

    @Column(name = "price", nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer price;

    @Column(name = "isbn", nullable = false)
    @NotBlank
    @Pattern(regexp = "\\d{10}|\\d{13}")
    private String isbn;

    @Column(name = "publisher", nullable = false)
    @NotBlank
    private String publisher;

    @Column(name = "publish_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate publish_date;

    @Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    private Integer quantity;

    @Column(name = "availability", nullable = false)
    private Boolean availability;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER,
    cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Image> images = new ArrayList<>();

    public Book() {}

    public Book(Long id, String name, String authors, Integer price, String isbn, String publisher, LocalDate publish_date, String description, List<Image> images) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.description = description;
        this.images = images;
    }

    public Book(String name, String authors, Integer price, String isbn, String publisher, LocalDate publish_date,
                String description) {
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.description = description;
    }

    public Book(String name, String authors, Integer price, String isbn, String publisher, LocalDate publish_date, String description, Integer quantity, Boolean availability) {
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publish_date = publish_date;
        this.description = description;
        this.quantity = quantity;
        this.availability = availability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public LocalDate getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(LocalDate publish_date) {
        this.publish_date = publish_date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        this.availability = !(quantity == 0);
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", authors='" + authors + '\'' +
                ", price=" + price +
                ", isbn='" + isbn + '\'' +
                ", publisher='" + publisher + '\'' +
                ", publish_date=" + publish_date +
                ", description='" + description + '\'' +
                '}';
    }
}