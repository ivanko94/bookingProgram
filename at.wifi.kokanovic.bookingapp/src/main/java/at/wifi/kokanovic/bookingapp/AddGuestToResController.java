package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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

public class AddGuestToResController implements Initializable {

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private DatePicker dateOfBirth;

	@FXML
	private TextField gender;

	@FXML
	private TextField identityCardNr;

	@FXML
	private TextField street;

	@FXML
	private TextField city;

	@FXML
	private TextField zip;

	@FXML
	private TextField country;

	@FXML
	private TextField nationality;

	@FXML
	private Button back;

	@FXML
	private Button add;

	private Stage primaryStage;

	private long resIdFromOverview;

	private ObservableList<ReservationProperties> observableResPropFromOverview;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		dateOfBirth.setValue(LocalDate.now());
	}

	@FXML
	void addClicked(ActionEvent event) {
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

			primaryStage = (Stage) add.getScene().getWindow();
			primaryStage.close();

			ReservationPersistence.getInstance().addGuestsToRes(resIdFromOverview, firstName.getText(),
					lastName.getText(), dateOfBirth.getValue(), identityCardNr.getText(), street.getText(),
					city.getText(), zip.getText(), country.getText(), gender.getText(), nationality.getText());

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ReservationOverviewController newReservationOverviewController = fxmlLoader.getController();
			newReservationOverviewController.getTableView().setItems(observableResPropFromOverview);

			Parent root = fxmlLoader.getRoot();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setTitle("Reservation overview");
			stage.setResizable(false);
			stage.show();

		}
	}

	@FXML
	void backClicked(ActionEvent event) {

		primaryStage = (Stage) back.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ReservationOverviewController newReservationOverviewController = fxmlLoader.getController();
		newReservationOverviewController.getTableView().setItems(observableResPropFromOverview);

		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
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

	public TextField getGender() {
		return gender;
	}

	public void setGender(TextField gender) {
		this.gender = gender;
	}

	public TextField getIdentityCardNr() {
		return identityCardNr;
	}

	public void setIdentityCardNr(TextField identityCardNr) {
		this.identityCardNr = identityCardNr;
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

	public TextField getNationality() {
		return nationality;
	}

	public void setNationality(TextField nationality) {
		this.nationality = nationality;
	}

	public Button getBack() {
		return back;
	}

	public void setBack(Button back) {
		this.back = back;
	}

	public Button getAdd() {
		return add;
	}

	public void setAdd(Button add) {
		this.add = add;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public long getResIdFromOverview() {
		return resIdFromOverview;
	}

	public void setResIdFromOverview(long resIdFromOverview) {
		this.resIdFromOverview = resIdFromOverview;
	}

	public ObservableList<ReservationProperties> getObservableResPropFromOverview() {
		return observableResPropFromOverview;
	}

	public void setObservableResPropFromOverview(ObservableList<ReservationProperties> observableResPropFromOverview) {
		this.observableResPropFromOverview = observableResPropFromOverview;
	}

}
