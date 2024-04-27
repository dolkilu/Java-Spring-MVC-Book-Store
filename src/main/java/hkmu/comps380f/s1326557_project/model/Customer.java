package hkmu.comps380f.s1326557_project.model;

import hkmu.comps380f.s1326557_project.validator.ValidCountry;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "customer")
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(name = "firstname", nullable = false)
	@NotBlank
	@Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
	@Size(max = 50)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	@NotBlank
	@Pattern(regexp = "[\\p{L}\\s\\-.']+", message = "Name can only contain letters, spaces, hyphens, periods, and apostrophes.")
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
	@Pattern(regexp = "^[0-9]{0,15}$")
	private String phone;

	@Column(name = "country", nullable = false)
	@NotBlank
	@ValidCountry(message = "Invalid country selection")
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "username", referencedColumnName = "username")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BookUser user;

	public Customer() {
	}

	public Customer(Long id, String firstname, String lastname, String email, String phone, String country, String city, String address1, String address2, String postalCode, BookUser user) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.country = country;
		this.city = city;
		this.address1 = address1;
		this.address2 = address2;
		this.postalCode = postalCode;
		this.user = user;
	}

	public Customer(String firstname, String lastname, String email, String phone, String country, String city, String address1, String address2, String postalCode) {
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

	public BookUser getUser() {
		return user;
	}

	public void setUser(BookUser user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"id=" + id +
				", firstname='" + firstname + '\'' +
				", lastname='" + lastname + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				", country='" + country + '\'' +
				", city='" + city + '\'' +
				", address1='" + address1 + '\'' +
				", address2='" + address2 + '\'' +
				", postalCode='" + postalCode + '\'' +
				", user=" + user +
				'}';
	}
}
