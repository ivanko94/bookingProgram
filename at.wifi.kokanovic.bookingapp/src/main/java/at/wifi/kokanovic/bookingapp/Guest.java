package at.wifi.kokanovic.bookingapp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity

@Table(name = "guest")
@Access(value = AccessType.FIELD)
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idNumber")
	private long idNumber;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "dateOfBirth", columnDefinition = "DATE")
	private LocalDate dateOfBirth;

	@Column(name = "idCardNumber")
	private String idCardNumber;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "zip")
	private String zip;

	@Column(name = "country")
	private String country;

	@Column(name = "gender")
	private String gender;

	@Column(name = "nationality")
	private String nationality;

	@ManyToMany(mappedBy = "guest", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Reservation> reservation = new HashSet<Reservation>();

	@OneToMany(mappedBy = "guest")
	Set<ReservationGuest> relations = new HashSet<>();

	public Guest() {
	}

	public Guest(long idNumber, String firstName, String lastName, LocalDate dateOfBirth, String idCardNumber,
			String street, String city, String zip, String country, String gender, String nationality) {
		super();
		this.idNumber = idNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.idCardNumber = idCardNumber;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
		this.gender = gender;
		this.nationality = nationality;
	}

	public void addReservation(Reservation resToAdd) {

		this.getReservation().add(resToAdd);
		resToAdd.getGuest().add(this);

	}

	public long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Set<Reservation> getReservation() {
		return reservation;
	}

	public void setReservation(Set<Reservation> reservation) {
		this.reservation = reservation;
	}

	@Override
	public String toString() {
		return idNumber + ": " + firstName + " " + lastName + " " + dateOfBirth + " " + street + " " + zip + " " + city
				+ " " + country + " " + nationality;

	}
	
	public String getDetails() {
		
		String details = this.getIdNumber() + ": " + this.getFirstName() + " " + this.getLastName();
		
		return details;
	}
}
