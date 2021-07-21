package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class StatisticController implements Initializable {

	@FXML
	private DatePicker fromDate;

	@FXML
	private DatePicker untilDate;

	@FXML
	private Button back;

	@FXML
	private Button show;

	@FXML
	private Label informationText;

	private Stage primaryStage;

	@Override

	public void initialize(URL location, ResourceBundle resources) {

		fromDate.setValue(LocalDate.now());
		untilDate.setValue(LocalDate.now());
		fromDate.setEditable(false);
		untilDate.setEditable(false);

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
		stage.setTitle("Start");
		stage.setResizable(false);
		stage.show();

	}

	@FXML
	void showClicked(ActionEvent event) {

		if ((fromDate.getValue().isAfter(untilDate.getValue()))
				|| (fromDate.getValue().isEqual(untilDate.getValue()))) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Incorrect date inputs!");

			alert.showAndWait();

		}

		else {
			// makes set from a list of all reservation 
			Set<Reservation> allRes = ReservationPersistence.getInstance().getAllReservations().stream()
					.collect(Collectors.toSet()); 

			// gets all irrelevant reservations, that will not be treated as a "stay-over" for the requested time period
			Set<Reservation> filteredRes = allRes
					.stream().filter(
							(res -> res.getCheckIn().isEqual(untilDate.getValue())
									|| res.getCheckIn().isAfter(untilDate.getValue())
									|| (res.getCheckOut().isEqual(fromDate.getValue())
											|| res.getCheckOut().isBefore(fromDate.getValue()))))
					.collect(Collectors.toSet()); 
			//removes all irrelevant reservations from all reservations and saves remaining reservations to a set
			Set<Reservation> relevantRes = allRes.stream().filter(res -> !filteredRes.contains(res))
					.collect(Collectors.toSet());
			// gets number of relevant reservations
			Long numberOfRelevantRes = relevantRes.stream().count();

			informationText.setText("The number of reservations for requested period is: " + numberOfRelevantRes);

		}
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

}
