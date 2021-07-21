package at.wifi.kokanovic.bookingapp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class RoomPersistence implements AutoCloseable {

	private static volatile RoomPersistence INSTANCE;
	private final static String PERSISTENCE_UNIT = "jpa_mariadb";
	private EntityManager em;

	EntityManagerFactory emf;

	public RoomPersistence(String persistenceUnit) {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
	}

	public static RoomPersistence getInstance() {
		if (INSTANCE == null)
			INSTANCE = new RoomPersistence(PERSISTENCE_UNIT);
		return INSTANCE;
	}
/**
 * creates or updates rooms in data base
 */
	public void initializeRooms() {
		
		Room room101 = new Room();
		room101.setRoomType(RoomType.SINGLE);
		room101.setRoomNumber("101");
		room101.setPrice(50.00);
		Room room102 = new Room();
		room102.setRoomType(RoomType.DOUBLE);
		room102.setRoomNumber("102");
		room102.setPrice(70.00);
		Room room103 = new Room();
		room103.setRoomType(RoomType.DOUBLE);
		room103.setRoomNumber("103");
		room103.setPrice(70.00);
		Room room104 = new Room();
		room104.setRoomType(RoomType.SINGLE);
		room104.setRoomNumber("104");
		room104.setPrice(50.00);
		Room room105 = new Room();
		room105.setRoomType(RoomType.TRIPLE);
		room105.setRoomNumber("105");
		room105.setPrice(100.00);
		Room room106 = new Room();
		room106.setRoomType(RoomType.QUARDAPLE);
		room106.setRoomNumber("106");
		room106.setPrice(130.00);

		List<Room> roomList = new ArrayList<Room>();
		roomList.add(room101);
		roomList.add(room102);
		roomList.add(room103);
		roomList.add(room104);
		roomList.add(room105);
		roomList.add(room106);

		em.getTransaction().begin();
		roomList.forEach(em::merge);
		em.getTransaction().commit();
	}

	public List<Room> selectAllRooms() {

		TypedQuery<Room> selectAllRoomsQuery = em.createQuery("SELECT r FROM Room r", Room.class);
		List<Room> allRooms = selectAllRoomsQuery.getResultList();

		return allRooms;

	}
/**
 * gets all free rooms for requested type and time period
 * 
 * @param requestedType
 * @param requestedCheckIn
 * @param requestedCheckout
 * @return list of free room NUMBERS!
 */
	public List<String> getFreeRoomsList(RoomType requestedType, LocalDate requestedCheckIn,
			LocalDate requestedCheckout) {

		List<Room> allRooms = selectAllRooms();
		List<Room> requestedTypeRooms = new ArrayList<Room>();
		List<String> requestedTypeRoomNumbers = new ArrayList<String>();

		for (Room room : allRooms) {
			if (room.getRoomType().equals(requestedType)) {

				requestedTypeRooms.add(room);

			}

		}

		for (Room room : requestedTypeRooms) {
			String roomNumber = room.getRoomNumber();
			requestedTypeRoomNumbers.add(roomNumber);

		}

		List<Reservation> allRes = ReservationPersistence.getInstance().getAllReservations();
		List<Room> occupiedRooms = new ArrayList<Room>();
		List<String> occupiedRoomNumbers = new ArrayList<String>();

		for (Reservation res : allRes) {
			if ((res.getCheckOut().compareTo(requestedCheckIn) > 0)
					&& (res.getCheckIn().compareTo(requestedCheckout) < 0)) {

				Room occupiedRoom = res.getRoom();
				occupiedRooms.add(occupiedRoom);

			}
			for (Room room : occupiedRooms) {

				String roomNumber = room.getRoomNumber();
				occupiedRoomNumbers.add(roomNumber);
			}
		}

		requestedTypeRoomNumbers.removeAll(occupiedRoomNumbers);
		List<String> freeRoomNumbers = requestedTypeRoomNumbers;
		return freeRoomNumbers;
	}

	public void close() throws Exception {
		em.close();
	}

}
