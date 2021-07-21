package at.wifi.kokanovic.bookingapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GuestPersistence implements AutoCloseable {
	private static volatile GuestPersistence INSTANCE;
	private final static String PERSISTENCE_UNIT = "jpa_mariadb";
	private EntityManager em;

	EntityManagerFactory emf;

	public GuestPersistence(String persistenceUnit) {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
	}

	public static GuestPersistence getInstance() {
		if (INSTANCE == null)
			INSTANCE = new GuestPersistence(PERSISTENCE_UNIT);
		return INSTANCE;
	}

	/**
	 * saves NEW guest
	 * 
	 * @param firstName
	 * @param lastName
	 * @param dateOfBirth
	 * @param idCardNumber
	 * @param street
	 * @param city
	 * @param zip
	 * @param country
	 * @param gender
	 * @param nationality
	 * @return id of the new guest after insert
	 */
	public long saveGuest(String firstName, String lastName, LocalDate dateOfBirth, String idCardNumber, String street,
			String city, String zip, String country, String gender, String nationality) {
		Guest guest = new Guest();
		guest.setFirstName(firstName);
		guest.setLastName(lastName);
		if (dateOfBirth == null) {
			guest.setDateOfBirth(LocalDate.now());
		} else {
			guest.setDateOfBirth(dateOfBirth);
		}
		guest.setIdCardNumber(idCardNumber);
		guest.setStreet(street);
		guest.setCity(city);
		guest.setZip(zip);
		guest.setCountry(country);
		guest.setGender(gender);
		guest.setNationality(nationality);

		em.getTransaction().begin();
		em.persist(guest);
		em.getTransaction().commit();

		return guest.getIdNumber();

	}
/**
 * gets allGuest from data base
 * 
 * @return a list of all guests
 */
	public List<Guest> getAllGuests() {

		TypedQuery<Guest> getAllGuestsQuery = em.createQuery("SELECT g FROM Guest g", Guest.class);
		List<Guest> allGuests = getAllGuestsQuery.getResultList();
		return allGuests;
	}
/**
 * finds a guest by his id number
 * 
 * @param guestId
 * @return a list of found guests ( it can be only one though) with requested id 
 * @throws NumberFormatException
 */
	public List<Guest> findByGuestId(long guestId) throws NumberFormatException {

		List<Guest> allGuests = getAllGuests();
		List<Guest> foundGuest = new ArrayList<Guest>();

		int lastGuestIndex = allGuests.size() - 1;

		for (int i = 0; i < allGuests.size(); i++) {

			if (guestId == allGuests.get(i).getIdNumber()) {

				foundGuest.add(allGuests.get(i));
				break;

			} else {

				if (i == lastGuestIndex) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText("The requested guestId does not exist. Please try again!");

					alert.showAndWait();
				}
			}
		}

		return foundGuest;

	}
/**
 * finds a guest by his first name
 * @param firstName
 * @return list of guests with requested first name
 */
	public List<Guest> findByFirstName(String firstName) {

		List<Guest> allGuests = getAllGuests();
		int lastGuestIndex = allGuests.size() - 1;
		List<Guest> foundGuests = new ArrayList<Guest>();

		for (int i = 0; i < allGuests.size(); i++) {

			if (firstName.equalsIgnoreCase(allGuests.get(i).getFirstName())) {

				foundGuests.add(allGuests.get(i));

			} else {
				if (i == lastGuestIndex && foundGuests.isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(
							"The guest with the first name " + firstName + " does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundGuests;
	}
/**
 * finds a guest by his last name
 * @param lastName
 * @return list of guests with requested last name
 */
	public List<Guest> findByLastName(String lastName) {  

		List<Guest> allGuests = getAllGuests();
		int lastGuestIndex = allGuests.size() - 1;
		List<Guest> foundGuests = new ArrayList<Guest>();

		for (int i = 0; i < allGuests.size(); i++) {

			if (lastName.equalsIgnoreCase(allGuests.get(i).getLastName())) {

				foundGuests.add(allGuests.get(i));

			} else {
				if (i == lastGuestIndex && foundGuests.isEmpty()) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information");
					alert.setHeaderText(null);
					alert.setContentText(
							"The guest with the last name " + lastName + " does not exist. Please try again!");
					alert.showAndWait();
				}
			}
		}
		return foundGuests;
	}
/**
 * Modifies the first name of the guest and saves changes
 * @param newValue
 * @param id
 */
	public void changeFirstName(String newValue, String id) {

		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {
				guest.setFirstName(newValue);

			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}
/**
 * Modifies the last name of the guest and saves changes
 * @param newValue
 * @param id
 */
	public void changeLastName(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setLastName(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeDateOfBirth(LocalDate newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setDateOfBirth(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeGender(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setGender(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeIdCardNumber(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setIdCardNumber(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeNationality(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setNationality(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeStreet(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setStreet(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeCity(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setCity(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeZip(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setZip(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void changeCountry(String newValue, String id) {
		List<Guest> allGuests = getAllGuests();
		for (Guest guest : allGuests) {
			if (guest.getIdNumber() == Long.parseLong(id)) {

				guest.setCountry(newValue);
			}
			em.getTransaction().begin();
			em.persist(guest);
			em.getTransaction().commit();

		}

	}

	public void deleteGuest(long idNumber) {

		Guest guestToDelete = em.find(Guest.class, idNumber);

		Set<Reservation> guestsRes = guestToDelete.getReservation();
		Iterator<Reservation> it = guestsRes.iterator();

		while (it.hasNext()) {

			Reservation res = it.next();
			int numberOfGuests = res.getGuest().size();

			if (numberOfGuests <= 1) {
				em.getTransaction().begin();

				res.getGuest().forEach(guest -> {
					guest.getReservation().remove(res);
				});
				em.remove(res);
				em.getTransaction().commit();
			}

			else {

				res.removeGuest(guestToDelete);

			}

		}

		em.getTransaction().begin();
		em.remove(guestToDelete);

		em.getTransaction().commit();

	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public void close() throws Exception {

		em.close();
	}

}
