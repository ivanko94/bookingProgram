package at.wifi.kokanovic.bookingapp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservation_guest")
public class ReservationGuest {

	@Id
	Long id;

	@ManyToOne
	@JoinColumn(name = "reservation_reservationId")
	Reservation reservation;

	@ManyToOne
	@JoinColumn(name = "guest_idNumber")
	Guest guest;

	public ReservationGuest() {

	}

	public ReservationGuest(Long id, Reservation reservation, Guest guest) {
		super();
		this.id = id;
		this.reservation = reservation;
		this.guest = guest;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

}
