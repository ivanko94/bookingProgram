package at.wifi.kokanovic.bookingapp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reservationId;

	private String resName;

	private LocalDate checkIn;
	private LocalDate checkOut;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "reservation_guest", joinColumns = {
			@JoinColumn(name = "reservation_reservationId") }, inverseJoinColumns = {
					@JoinColumn(name = "guest_idNumber") })
	private Set<Guest> guest = new HashSet<Guest>();

	@ManyToOne
	@JoinColumn(name = "roomId", referencedColumnName = "roomNumber")
	private Room room;

	@OneToMany(mappedBy = "reservation")
	Set<ReservationGuest> relations = new HashSet<>();

	public Reservation() {

	}

	public void addGuest(Guest guestToAdd) {

		this.getGuest().add(guestToAdd);
		guestToAdd.getReservation().add(this);

	}

	public void removeGuest(Guest guestToRemove) {
		this.getGuest().remove(guestToRemove);
		guestToRemove.getReservation().remove(this);

	}

	public long getReservationId() {
		return reservationId;
	}

	public void setReservationId(long reservationId) {
		this.reservationId = reservationId;
	}

	public LocalDate getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(LocalDate checkIn) {
		this.checkIn = checkIn;
	}

	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}

	public Set<Guest> getGuest() {
		return guest;
	}

	public void setGuest(Set<Guest> guest) {
		this.guest = guest;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

}
