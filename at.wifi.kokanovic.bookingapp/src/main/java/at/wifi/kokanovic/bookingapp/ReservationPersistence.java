package at.wifi.kokanovic.bookingapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ReservationPersistence implements AutoCloseable {

	private static volatile ReservationPersistence INSTANCE;
	private final static String PERSISTENCE_UNIT = "jpa_mariadb";

	private EntityManager em;

	EntityManagerFactory emf;

	public ReservationPersistence(String persistenceUnit) {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
	}

	public static ReservationPersistence getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ReservationPersistence(PERSISTENCE_UNIT);
		return INSTANCE;
	}

	public void saveReservation(String resName, LocalDate checkIn, LocalDate checkOut, String roomNumber,
			long idNumber) {

		Room room = em.getReference(Room.class, roomNumber);
		Guest guest = em.find(Guest.class, idNumber);

		em.getTransaction().begin();

		Reservation reservation = new Reservation();
		reservation.setResName(resName);
		reservation.setCheckIn(checkIn);
		reservation.setCheckOut(checkOut);
		reservation.setRoom(room);
		guest.addReservation(reservation);
		reservation.addGuest(guest);

		em.persist(reservation);
		em.persist(guest);
		em.getTransaction().commit();

	}

	public void addGuestsToRes(long reservationId, String firstName, String lastName, LocalDate dateOfBirth,
			String idCardNumber, String street, String city, String zip, String country, String gender,
			String nationality) {

		Reservation res = em.find(Reservation.class, reservationId);

		em.getTransaction().begin();

		Guest guestToAdd = new Guest();
		guestToAdd.setFirstName(firstName);
		guestToAdd.setLastName(lastName);
		if (dateOfBirth == null) {
			guestToAdd.setDateOfBirth(LocalDate.now());
		} else {
			guestToAdd.setDateOfBirth(dateOfBirth);
		}
		guestToAdd.setIdCardNumber(idCardNumber);
		guestToAdd.setStreet(street);
		guestToAdd.setCity(city);
		guestToAdd.setZip(zip);
		guestToAdd.setCountry(country);
		guestToAdd.setGender(gender);
		guestToAdd.setNationality(nationality);

		guestToAdd.addReservation(res);
		res.addGuest(guestToAdd);
		em.persist(guestToAdd);
		em.persist(res);
		em.getTransaction().commit();

	}

	public List<Reservation> getAllReservations() {

		TypedQuery<Reservation> getAllReservationsQuery = em.createQuery("SELECT r FROM Reservation r",
				Reservation.class);
		List<Reservation> allRes = getAllReservationsQuery.getResultList();

		return allRes;

	}

	public List<Reservation> findByResId(long resId) throws NumberFormatException {

		List<Reservation> allRes = getAllReservations();
		List<Reservation> foundRes = new ArrayList<Reservation>();
		int lastResIndex = allRes.size() - 1;

		for (int i = 0; i < allRes.size(); i++) {
			if (resId == allRes.get(i).getReservationId()) {
				foundRes.add(allRes.get(i));
				break;

			} else {

				if (i == lastResIndex) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("The requested reservation ID does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundRes;
	}

	public List<Reservation> findByResName(String resName) {

		List<Reservation> allRes = getAllReservations();
		List<Reservation> foundRes = new ArrayList<Reservation>();
		int lastResIndex = allRes.size() - 1;

		for (int i = 0; i < allRes.size(); i++) {

			if (resName.equalsIgnoreCase(allRes.get(i).getResName())) {

				foundRes.add(allRes.get(i));

			} else {

				if (i == lastResIndex && foundRes.isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(
							"The reservation with the name " + resName + " does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundRes;
	}

	public List<Reservation> findByCheckIn(LocalDate checkIn) {

		List<Reservation> allRes = getAllReservations();
		List<Reservation> foundRes = new ArrayList<Reservation>();
		int lastResIndex = allRes.size() - 1;

		for (int i = 0; i < allRes.size(); i++) {

			if (checkIn.equals(allRes.get(i).getCheckIn())) {

				foundRes.add(allRes.get(i));

			} else {

				if (i == lastResIndex && foundRes.isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(
							"The reservation with the check-in " + checkIn + " does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundRes;
	}

	public List<Reservation> findByCheckOut(LocalDate checkOut) {

		List<Reservation> allRes = getAllReservations();
		List<Reservation> foundRes = new ArrayList<Reservation>();
		int lastResIndex = allRes.size() - 1;

		for (int i = 0; i < allRes.size(); i++) {

			if (checkOut.equals(allRes.get(i).getCheckOut())) {
				foundRes.add(allRes.get(i));

			} else {

				if (i == lastResIndex && foundRes.isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(
							"The reservation with the check-in " + checkOut + " does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundRes;
	}

	public void changeName(String newValue, String id) {

		List<Reservation> allRes = getAllReservations();

		for (Reservation res : allRes) {
			if (res.getReservationId() == Long.parseLong(id)) {
				res.setResName(newValue);

			}
			em.getTransaction().begin();
			em.persist(res);
			em.getTransaction().commit();
		}
	}

	public void changeCheckIn(LocalDate newValue, String id) {

		List<Reservation> allRes = getAllReservations();

		for (Reservation res : allRes) {
			if (res.getReservationId() == Long.parseLong(id)) {
				res.setCheckIn(newValue);

			}
			em.getTransaction().begin();
			em.persist(res);
			em.getTransaction().commit();
		}
	}

	public void changeCheckOut(LocalDate newValue, String id) {

		List<Reservation> allRes = getAllReservations();

		for (Reservation res : allRes) {
			if (res.getReservationId() == Long.parseLong(id)) {
				res.setCheckOut(newValue);

			}
			em.getTransaction().begin();
			em.persist(res);
			em.getTransaction().commit();
			em.clear();
		}
	}

	public void changeRoomNr(String newValue, String id) {

		List<Reservation> allRes = getAllReservations();

		for (Reservation res : allRes) {
			if (res.getReservationId() == Long.parseLong(id)) {

				Room room = new Room();
				room.setRoomNumber(newValue);

				res.setRoom(room);

			}
			em.getTransaction().begin();
			em.persist(res);
			em.getTransaction().commit();
		}
	}

	public void deleteRes(String reservationId) {

		Long parsedResId = Long.parseLong(reservationId);
		Reservation resToRemove = em.find(Reservation.class, parsedResId);

		em.getTransaction().begin();

		resToRemove.getGuest().forEach(guest -> {
			guest.getReservation().remove(resToRemove);
		});

		em.remove(resToRemove);

		em.getTransaction().commit();

	}

	public void close() throws Exception {
		em.close();

	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
