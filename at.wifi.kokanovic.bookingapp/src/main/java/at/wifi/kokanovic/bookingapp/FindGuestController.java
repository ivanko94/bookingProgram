package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FindGuestController {

	@FXML
	private TextField byGuestId;

	@FXML
	private TextField byFirstName;

	@FXML
	private TextField byLastName;

	@FXML
	private Button showAllGuestsButton;

	@FXML
	private Button back;

	@FXML
	private Button next;

	private Stage primaryStage;

	@FXML
	void backClicked(ActionEvent event) {
		primaryStage = (Stage) back.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("SearchGuestOrReservation.fxml"));

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

	@FXML
	void nextClicked(ActionEvent event) {

		if (!byGuestId.getText().isEmpty() && byFirstName.getText().isEmpty() && byLastName.getText().isEmpty()) {

			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Guest> foundGuests = new ArrayList<Guest>();
			try {

				foundGuests = GuestPersistence.getInstance().findByGuestId(Long.parseLong(byGuestId.getText()));

			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("UNVALID ID!");

				alert.showAndWait();

			}

			List<GuestProperties> foundGuestsProp = new ArrayList<GuestProperties>();

			for (Guest guest : foundGuests) {

				GuestProperties guestProp = new GuestProperties();

				guestProp.idNumberProp().setValue(Long.toString(guest.getIdNumber()));
				guestProp.firstNameProp().setValue(guest.getFirstName());
				guestProp.lastNameProp().setValue(guest.getLastName());
				guestProp.dateOfBirthProp().setValue(guest.getDateOfBirth().toString());
				guestProp.genderProp().setValue(guest.getGender());
				guestProp.idCardNumberProp().setValue(guest.getIdCardNumber());
				guestProp.streetProp().setValue(guest.getStreet());
				guestProp.cityProp().setValue(guest.getCity());
				guestProp.zipProp().setValue(guest.getZip());
				guestProp.countryProp().setValue(guest.getCountry());
				guestProp.nationalityProp().setValue(guest.getNationality());

				foundGuestsProp.add(guestProp);

			}
			ObservableList<GuestProperties> observableFoundGuestProp = FXCollections
					.observableArrayList(foundGuestsProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("GuestsOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			GuestOverviewController gC = fxmlLoader.<GuestOverviewController>getController();
			gC.getTableView().setItems(observableFoundGuestProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Guests overview - by guest ID");
			stage.show();

		}

		else if (!byFirstName.getText().isEmpty() && byGuestId.getText().isEmpty() && byLastName.getText().isEmpty()) {
			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Guest> foundGuests = GuestPersistence.getInstance().findByFirstName(byFirstName.getText());
			List<GuestProperties> foundGuestsProp = new ArrayList<GuestProperties>();

			for (Guest guest : foundGuests) {
				GuestProperties guestProp = new GuestProperties();

				guestProp.idNumberProp().setValue(Long.toString(guest.getIdNumber()));
				guestProp.firstNameProp().setValue(guest.getFirstName());
				guestProp.lastNameProp().setValue(guest.getLastName());
				guestProp.dateOfBirthProp().setValue(guest.getDateOfBirth().toString());
				guestProp.genderProp().setValue(guest.getGender());
				guestProp.idCardNumberProp().setValue(guest.getIdCardNumber());
				guestProp.streetProp().setValue(guest.getStreet());
				guestProp.cityProp().setValue(guest.getCity());
				guestProp.zipProp().setValue(guest.getZip());
				guestProp.countryProp().setValue(guest.getCountry());
				guestProp.nationalityProp().setValue(guest.getNationality());

				foundGuestsProp.add(guestProp);
			}
			ObservableList<GuestProperties> observableFoundGuestProp = FXCollections
					.observableArrayList(foundGuestsProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("GuestsOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			GuestOverviewController gC = fxmlLoader.<GuestOverviewController>getController();
			gC.getTableView().setItems(observableFoundGuestProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Guests overview - by first name");
			stage.show();

		} else if (!byLastName.getText().isEmpty() && byGuestId.getText().isEmpty()
				&& byFirstName.getText().isEmpty()) {
			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Guest> foundGuests = GuestPersistence.getInstance().findByLastName(byLastName.getText());
			List<GuestProperties> foundGuestsProp = new ArrayList<GuestProperties>();

			for (Guest guest : foundGuests) {
				GuestProperties guestProp = new GuestProperties();

				guestProp.idNumberProp().setValue(Long.toString(guest.getIdNumber()));
				guestProp.firstNameProp().setValue(guest.getFirstName());
				guestProp.lastNameProp().setValue(guest.getLastName());
				guestProp.dateOfBirthProp().setValue(guest.getDateOfBirth().toString());
				guestProp.genderProp().setValue(guest.getGender());
				guestProp.idCardNumberProp().setValue(guest.getIdCardNumber());
				guestProp.streetProp().setValue(guest.getStreet());
				guestProp.cityProp().setValue(guest.getCity());
				guestProp.zipProp().setValue(guest.getZip());
				guestProp.countryProp().setValue(guest.getCountry());
				guestProp.nationalityProp().setValue(guest.getNationality());

				foundGuestsProp.add(guestProp);
			}
			ObservableList<GuestProperties> observableFoundGuestProp = FXCollections
					.observableArrayList(foundGuestsProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("GuestsOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			GuestOverviewController gC = fxmlLoader.<GuestOverviewController>getController();
			gC.getTableView().setItems(observableFoundGuestProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Guests overview - by last name");
			stage.show();

		}

		else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Searching is possible only by using ONE filter!");

			alert.showAndWait();

		}

	}

	@FXML
	void showAllGuestsButtonClicked(ActionEvent event) {

		primaryStage = (Stage) showAllGuestsButton.getScene().getWindow();
		primaryStage.close();

		List<Guest> allGuests = GuestPersistence.getInstance().getAllGuests();
		List<GuestProperties> allGuestProp = new ArrayList<GuestProperties>();
		for (Guest guest : allGuests) {

			GuestProperties guestProp = new GuestProperties();

			guestProp.idNumberProp().setValue(Long.toString(guest.getIdNumber()));
			guestProp.firstNameProp().setValue(guest.getFirstName());
			guestProp.lastNameProp().setValue(guest.getLastName());
			guestProp.dateOfBirthProp().setValue(guest.getDateOfBirth().toString());
			guestProp.genderProp().setValue(guest.getGender());
			guestProp.idCardNumberProp().setValue(guest.getIdCardNumber());
			guestProp.streetProp().setValue(guest.getStreet());
			guestProp.cityProp().setValue(guest.getCity());
			guestProp.zipProp().setValue(guest.getZip());
			guestProp.countryProp().setValue(guest.getCountry());
			guestProp.nationalityProp().setValue(guest.getNationality());

			allGuestProp.add(guestProp);
		}

		ObservableList<GuestProperties> observableGuestProp = FXCollections.observableArrayList(allGuestProp);

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("GuestsOverview.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		GuestOverviewController gC = fxmlLoader.<GuestOverviewController>getController();
		gC.getTableView().setItems(observableGuestProp);

		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Guests overview - all guests");
		stage.show();
	}

}
