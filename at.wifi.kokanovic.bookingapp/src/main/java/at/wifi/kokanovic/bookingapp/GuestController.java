package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
//import org.controlsfx.control.textfield.TextFields;

public class GuestController implements Initializable {

	@FXML
	private TextField searchForExistingGuest;
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private DatePicker dateOfBirth;
	@FXML
	private TextField identityCardNr;
	@FXML
	private TextField nationality;
	@FXML
	private TextField street;
	@FXML
	private TextField city;
	@FXML
	private TextField zip;
	@FXML
	private TextField country;
	@FXML
	private TextField gender;
	@FXML
	private Button next;
	@FXML
	private Button back;

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		dateOfBirth.setValue(LocalDate.now());
		dateOfBirth.setEditable(false);
		
		
/*
 * This works only with controlsfx 8.40.14 and java 8 and beyond!!!
 * 
 * List<Guest> allGuests = GuestPersistence.getInstance().getAllGuests();

		List<String> allGuestsToString = allGuests.stream().map(Guest::toString).collect(Collectors.toList());

		TextFields.bindAutoCompletion(searchForExistingGuest, allGuestsToString);
 */
		

	}

	@FXML
	void nextClicked(ActionEvent event) {

		try {

			if (firstName.getText().isEmpty() || lastName.getText().isEmpty()) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("Please enter a first name AND a last name to continue!");

				alert.showAndWait();
			} else if (dateOfBirth.getValue().isAfter(LocalDate.now())) {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText(null);
				alert.setContentText("The Date of birth cannot be a future date");

				alert.showAndWait();

			} else {

				primaryStage = (Stage) next.getScene().getWindow();
				primaryStage.close();

				long currentGuestId = GuestPersistence.getInstance().saveGuest(firstName.getText(), lastName.getText(),
						dateOfBirth.getValue(), identityCardNr.getText(), street.getText(), city.getText(),
						zip.getText(), country.getText(), gender.getText(), nationality.getText());

				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("NewReservation.fxml"));

				try {
					fxmlLoader.load();
				} catch (IOException e) {
					e.printStackTrace();
				}

				NewReservationController newReservationController = fxmlLoader.getController();
				newReservationController.setGuestIdFromGuestController(currentGuestId);

				Parent root = fxmlLoader.getRoot();

				Scene scene = new Scene(root);
				Stage stage = new Stage();

				stage.setScene(scene);
				stage.setTitle("New Reservation");
				stage.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	void backClicked(ActionEvent event) {

		primaryStage = (Stage) back.getScene().getWindow();
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

	@FXML
	void searchFinished(ActionEvent event) {
		
		/*
		 * Feature has not been implemented jet. The code refers to the "TextFields.bindAutoCompletion" which is only possible with java 8 or beyond!!! 
		 * 
		 * 
		 * int secondIndex = takeIdNumberFromSuggestion(searchForExistingGuest.getText());
		 * String idFromSuggestion;

		if (secondIndex == 0) {
			idFromSuggestion = searchForExistingGuest.getText().substring(0, 1);

		} else {
			idFromSuggestion = searchForExistingGuest.getText().substring(0, secondIndex);

		}

		primaryStage = (Stage) searchForExistingGuest.getScene().getWindow();

		primaryStage.close();

		Guest foundGuest = GuestPersistence.getInstance().findByGuestId(Long.parseLong(idFromSuggestion)).get(0);

		GuestProperties foundGuestProp = new GuestProperties();

		foundGuestProp.idNumberProp().setValue(Long.toString(foundGuest.getIdNumber()));
		foundGuestProp.firstNameProp().setValue(foundGuest.getFirstName());
		foundGuestProp.lastNameProp().setValue(foundGuest.getLastName());
		foundGuestProp.dateOfBirthProp().setValue(foundGuest.getDateOfBirth().toString());
		foundGuestProp.genderProp().setValue(foundGuest.getGender());
		foundGuestProp.idCardNumberProp().setValue(foundGuest.getIdCardNumber());
		foundGuestProp.streetProp().setValue(foundGuest.getStreet());
		foundGuestProp.cityProp().setValue(foundGuest.getCity());
		foundGuestProp.zipProp().setValue(foundGuest.getZip());
		foundGuestProp.countryProp().setValue(foundGuest.getCountry());
		foundGuestProp.nationalityProp().setValue(foundGuest.getNationality());

		List<GuestProperties> foundGuestPropList = new ArrayList<GuestProperties>();
		foundGuestPropList.add(foundGuestProp);

		ObservableList<GuestProperties> observableFoundGuestProp = FXCollections.observableArrayList(foundGuestProp);

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("GuestsOverview.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		GuestOverviewController newGuestOverviewController = fxmlLoader.getController();
		newGuestOverviewController.getTableView().setItems(observableFoundGuestProp);
		;

		Parent root = fxmlLoader.getRoot();

		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Guest overview");
		stage.show();
		 */
		
	}

	
	public int takeIdNumberFromSuggestion(String suggestion) { // it takes id number of guest from suggestion and uses it to find the guest. The feature search for existing guest has not been implemented jet.
		char colonForSure = ':';
		int indexOfColon = 0;
		for (int i = 0; i <= (suggestion.length() - 1); i++) {
			char maybeColon = suggestion.charAt(i);

			if (maybeColon == colonForSure) {

				indexOfColon = suggestion.indexOf(maybeColon);

			}

		}

		return indexOfColon;

	}

	public TextField getFirstName() {
		return firstName;
	}

	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	public DatePicker getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(DatePicker dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public TextField getIdentityCardNr() {
		return identityCardNr;
	}

	public void setIdentityCardNr(TextField identityCardNr) {
		this.identityCardNr = identityCardNr;
	}

	public TextField getNationality() {
		return nationality;
	}

	public void setNationality(TextField nationality) {
		this.nationality = nationality;
	}

	public TextField getStreet() {
		return street;
	}

	public void setStreet(TextField street) {
		this.street = street;
	}

	public TextField getCity() {
		return city;
	}

	public void setCity(TextField city) {
		this.city = city;
	}

	public TextField getZip() {
		return zip;
	}

	public void setZip(TextField zip) {
		this.zip = zip;
	}

	public TextField getCountry() {
		return country;
	}

	public void setCountry(TextField country) {
		this.country = country;
	}

	public TextField getGender() {
		return gender;
	}

	public void setGender(TextField gender) {
		this.gender = gender;
	}

	public Button getNext() {
		return next;
	}

	public void setNext(Button next) {
		this.next = next;
	}

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

}
