package hkmu.comps380f.s1326557_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "username", nullable = false)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private BookUser user;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "firstname", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String firstname;

    @Column(name = "lastname", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String lastname;

    @Column(name = "email", nullable = false)
    @NotBlank
    @Size(max = 254)
    @Email
    private String email;

    @Column(name = "phone", nullable = false)
    @NotBlank
    @Size(max = 15)
    private String phone;

    @Column(name = "country", nullable = false)
    @NotBlank
    @Size(max = 55)
    private String country;

    @Column(name = "city", nullable = false)
    @NotBlank
    @Size(max = 60)
    private String city;

    @Column(name = "address1", nullable = false)
    @NotBlank
    @Size(max = 100)
    private String address1;

    @Column(name = "address2")
    @Size(max = 100)
    private String address2;

    @Column(name = "postal_code")
    @Size(max = 18)
    private String postalCode;

    public Order() {
    }


    public Order(Long id, String orderDate, BookUser user, BigDecimal total, String firstname, String lastname, String email, String phone, String country, String city, String address1, String address2, String postalCode) {
        this.id = id;
        this.orderDate = orderDate;
        this.user = user;
        this.total = total;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.country = country;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode = postalCode;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BookUser getUser() {
        return user;
    }

    public void setUser(BookUser user) {
        this.user = user;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", user=" + user +
                ", total=" + total +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}