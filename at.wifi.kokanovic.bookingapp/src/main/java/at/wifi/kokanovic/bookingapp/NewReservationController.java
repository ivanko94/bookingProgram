package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NewReservationController implements Initializable {

	@FXML
	private TextField resName;
	@FXML
	private DatePicker checkIn;

	@FXML
	private DatePicker checkOut;

	@FXML
	private Button cancel;

	@FXML
	private Button finish;

	@FXML
	private ChoiceBox<RoomType> roomTypeChoiceBox;

	@FXML
	private ChoiceBox<String> roomNumberChoiceBox;

	private Stage primaryStage;

	private long guestIdFromGuestController;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		RoomPersistence.getInstance().initializeRooms();

		checkIn.setValue(LocalDate.now());
		checkOut.setValue(LocalDate.now());
		checkIn.setEditable(false);
		checkOut.setEditable(false);

		roomTypeChoiceBox.getItems().setAll(RoomType.values());

		roomTypeChoiceBox.setValue(RoomType.SINGLE);

		roomNumberChoiceBox.setValue("");

		roomNumberChoiceBox.getItems().setAll(FXCollections.observableArrayList(RoomPersistence.getInstance()
				.getFreeRoomsList(roomTypeChoiceBox.getValue(), checkIn.getValue(), checkOut.getValue())));

	}

	@FXML
	void chooseRoomType(MouseEvent event) {
		roomTypeChoiceBox.show();

	}

	@FXML
	void chooseRoomNr(MouseEvent event) {

		if (roomNumberChoiceBox.getValue() != null) {
			roomNumberChoiceBox.getItems().setAll(FXCollections.observableArrayList(RoomPersistence.getInstance()
					.getFreeRoomsList(roomTypeChoiceBox.getValue(), checkIn.getValue(), checkOut.getValue())));
		} else {
			roomNumberChoiceBox.setValue("");
		}
		roomNumberChoiceBox.show();
	}

	@FXML
	void cancelClicked(ActionEvent event) {
		primaryStage = (Stage) cancel.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("Start.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Start");
		stage.setResizable(false);
		stage.show();

	}

	@FXML
	void finishClicked(ActionEvent event) {
		if (resName.getText().isEmpty() || checkIn.getValue() == null || checkOut.getValue() == null) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please fill out all fields to continue!");

			alert.showAndWait();
		} else if (checkIn.getValue().isBefore(LocalDate.now())) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("The check-in cannot be in the past");

			alert.showAndWait();
		} else if (checkOut.getValue().isBefore(LocalDate.now())) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("The check-out cannot be in the past");

			alert.showAndWait();
		}

		else if (checkIn.getValue().isAfter(checkOut.getValue())) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("The check-in cannot be after the check-out");

			alert.showAndWait();
		}

		else if (checkOut.getValue().isBefore(checkIn.getValue())) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("The check-out cannot be before the check-in");

			alert.showAndWait();

		} else if (checkOut.getValue().isEqual(checkIn.getValue())) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Check-in date and check-out date cannot be the same!");

			alert.showAndWait();
		} else if (roomNumberChoiceBox.getValue().equals("")) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText(null);
			alert.setContentText("Please choose the room number to continue!");

			alert.showAndWait();

		} else if (checkIfRoomIsOccupied() == true) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("WARNING");
			alert.setHeaderText(null);
			alert.setContentText(
					"The requested room is occupied for requested time period. Please choose another room or change dates!");

			alert.showAndWait();

		} else {

			ReservationPersistence.getInstance().saveReservation(resName.getText(), checkIn.getValue(),
					checkOut.getValue(), roomNumberChoiceBox.getValue(), guestIdFromGuestController);

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Your reservation has been entered!");

			alert.showAndWait();

			primaryStage = (Stage) finish.getScene().getWindow();
			primaryStage.close();

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("Start.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
			Parent root = fxmlLoader.getRoot();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.show();

		}
	}
/**
 * checks in the data base if selected room may still be occupied 
 * @return true or false
 */
	public boolean checkIfRoomIsOccupied() {

		List<Reservation> allRes = ReservationPersistence.getInstance().getAllReservations();
		boolean bull = false;
		for (Reservation res : allRes) {

			if (!allRes.isEmpty()) {

				if (((roomNumberChoiceBox.getValue().equals(res.getRoom().getRoomNumber()))
						&& (((checkIn.getValue().isEqual(res.getCheckIn())
								&& checkOut.getValue().isEqual(res.getCheckOut()))
								|| (checkIn.getValue().isEqual(res.getCheckIn()))
								|| (checkOut.getValue().isEqual(res.getCheckOut())))))) {

					bull = true;
					break;

				}
			} else {
				bull = false;
			}
		}
		return bull;

	}

	public long getGuestIdFromGuestController() {
		return guestIdFromGuestController;
	}

	public void setGuestIdFromGuestController(long guestIdFromGuestController) {
		this.guestIdFromGuestController = guestIdFromGuestController;
	}

}
