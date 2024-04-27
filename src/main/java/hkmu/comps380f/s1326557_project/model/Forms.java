package hkmu.comps380f.s1326557_project.model;

import hkmu.comps380f.s1326557_project.validator.ValidCountry;
import jakarta.validation.constraints.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;


public class Forms {

    public static class CommentForm {
        @NotBlank
        private Long bookId;

        @NotBlank
        private String comment;

        public CommentForm() {
        }

        public CommentForm(Long bookId, String comment) {
            this.bookId = bookId;
            this.comment = comment;
        }

        public Long getBookId() {
            return bookId;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    public static class RegisterForm{
        @NotEmpty(message="Please enter your username.")
        @Pattern(regexp = "^[a-zA-Z0-9_-]{5,30}$", message = "Can only contain a-z, A-Z, 0-9, and these _- symbols.")
        @Size(min=5, max=30, message="Your password length must be between {min} and {max}.")
        private String username;

        @NotEmpty(message="Please enter your password.")
        @Size(min=6, max=30, message="Your password length must be between {min} and {max}.")
        private String password;

        @NotEmpty(message="Please confirm your password.")
        private String confirm_password;

        @NotEmpty(message = "Please select at least one role.")
        private String[] roles;

        // Customer details
        @NotEmpty(message = "Please enter your first name.")
        @Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
        @Size(max = 50, message = "First name must be less than {max} characters long.")
        private String firstname;

        @NotEmpty(message = "Please enter your last name.")
        @Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
        @Size(max = 50, message = "Last name must be less than {max} characters long.")
        private String lastname;


        @NotEmpty(message = "Please enter your email.")
        @Email(message = "Invalid email format.")
        private String email;

        @NotEmpty(message = "Please enter your phone number.")
        @Size(max = 15, message = "Phone number must be less than {max} characters long.")
        @Pattern(regexp = "^[0-9]{0,15}$", message ="Can only be numbers")
        private String phone;

        @NotEmpty(message = "Please select your country.")
        @ValidCountry(message = "Invalid country selection")
        private String country;

        @NotEmpty(message = "Please enter your city.")
        @Size(max = 60, message = "City must be less than {max} characters long.")
        private String city;

        @NotEmpty(message = "Please enter your address.")
        @Size(max = 100, message = "Address must be less than {max} characters long.")
        private String address1;

        @Size(max = 100, message = "Address must be less than {max} characters long.")
        private String address2;

        @Size(max = 18, message = "Postal code must be less than {max} characters long.")
        @Pattern(regexp = "^[a-zA-Z0-9-_]{0,10}$")
        private String postalCode;

        public RegisterForm() {
        }

        public RegisterForm(String username, String password, String confirm_password, @NotEmpty(message = "Please select at least one role.") String[] roles, String firstname, String lastname, String email, String phone, String country, String city, String address1, String address2, String postalCode) {
            this.username = username;
            this.password = password;
            this.confirm_password = confirm_password;
            this.roles = roles;
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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
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

    }

    public static class PasswordForm {
        @NotEmpty(message="Please enter your current password.")
        @Size(min=6, max=30, message="Your password length must be between {min} and {max}.")
        private String old_password;

        @NotEmpty(message="Please enter your new password.")
        @Size(min=6, max=30, message="Your password length must be between {min} and {max}.")
        private String new_password;

        @NotEmpty(message="Please confirm your new password.")
        private String confirm_password;

        public PasswordForm() {
        }

        public PasswordForm(String old_password, String new_password, String confirm_password) {
            this.old_password = old_password;
            this.new_password = new_password;
            this.confirm_password = confirm_password;
        }

        public String getOld_password() {
            return old_password;
        }

        public void setOld_password(String old_password) {
            this.old_password = old_password;
        }

        public String getNew_password() {
            return new_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }

        public String getConfirm_password() {
            return confirm_password;
        }

        public void setConfirm_password(String confirm_password) {
            this.confirm_password = confirm_password;
        }
    }

    public static class CustomerForm {
        @NotEmpty(message = "Please enter your first name.")
        @Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
        @Size(max = 50, message = "First name must be less than {max} characters long.")
        private String firstname;

        @NotEmpty(message = "Please enter your last name.")
        @Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
        @Size(max = 50, message = "Last name must be less than {max} characters long.")
        private String lastname;

        @NotEmpty(message = "Please enter your email.")
        @Email(message = "Invalid email format.")
        private String email;

        @NotEmpty(message = "Please enter your phone number.")
        @Size(max = 15, message = "Phone number must be less than {max} characters long.")
        @Pattern(regexp = "^[0-9]{0,15}$", message ="Can only be numbers")
        private String phone;

        @NotEmpty(message = "Please select your country.")
        @ValidCountry(message = "Invalid country selection")
        private String country;

        @NotEmpty(message = "Please enter your city.")
        @Size(max = 60, message = "City must be less than {max} characters long.")
        private String city;

        @NotEmpty(message = "Please enter your address.")
        @Size(max = 100, message = "Address must be less than {max} characters long.")
        private String address1;

        @Size(max = 100, message = "Address must be less than {max} characters long.")
        private String address2;

        @Size(max = 18, message = "Postal code must be less than {max} characters long.")
        @Pattern(regexp = "^[a-zA-Z0-9-_]{0,10}$")
        private String postalCode;

        public CustomerForm() {
        }

        public CustomerForm(String firstname, String lastname, String email, String phone, String country, String city, String address1, String address2, String postalCode) {
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
    }

    public static class BookForm {
        @NotBlank
        private String name;

        @NotBlank
        private String authors;

        @NotNull
        @PositiveOrZero
        private Integer price;

        @NotBlank
        @Pattern(regexp = "\\d{10}|\\d{13}", message = "Must be 10 or 13 digits")
        private String isbn;

        @NotBlank
        private String publisher;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull
        private LocalDate publish_date;

        private String description;

        @NotNull
        @PositiveOrZero
        private Integer quantity = 0;

        private Boolean availability = false;

        private MultipartFile[] images;

        public BookForm() {
        }

        public BookForm(String name, String authors, Integer price, String isbn, String publisher, LocalDate publish_date, String description, Integer quantity, Boolean availability, MultipartFile[] images) {
            this.name = name;
            this.authors = authors;
            this.price = price;
            this.isbn = isbn;
            this.publisher = publisher;
            this.publish_date = publish_date;
            this.description = description;
            this.quantity = quantity;
            this.availability = availability;
            this.images = images;
        }

        public BookForm(String name, String authors, Integer price, String isbn, String publisher, LocalDate publish_date, String description, Integer quantity, Boolean availability) {
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
        }

        public Boolean getAvailability() {
            return availability;
        }

        public void setAvailability(Boolean availability) {
            this.availability = availability;
        }

        public MultipartFile[] getImages() {
            return images;
        }

        public void setImages(MultipartFile[] images) {
            this.images = images;
        }
    }

    public static class RoleForm {
        @NotEmpty
        private List<String> roles;

        public RoleForm() {
        }

        public RoleForm(List<String> roles) {
            this.roles = roles;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }
}
