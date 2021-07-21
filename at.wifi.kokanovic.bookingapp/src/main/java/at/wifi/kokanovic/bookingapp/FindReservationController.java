package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FindReservationController {

	@FXML
	private TextField byResId;

	@FXML
	private TextField byResName;

	@FXML
	private DatePicker byCheckIn;

	@FXML
	private DatePicker byCheckOut;

	@FXML
	private Button showAllResButton;

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
		if (!byResId.getText().isEmpty() && byResName.getText().isEmpty() && byCheckIn.getValue() == null
				&& byCheckOut.getValue() == null) {

			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Reservation> foundRes = new ArrayList<Reservation>();

			try {

				foundRes = ReservationPersistence.getInstance().findByResId(Long.parseLong(byResId.getText()));

			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText(null);
				alert.setContentText("UNVALID ID!");

				alert.showAndWait();

			}

			List<ReservationProperties> foundResProp = new ArrayList<ReservationProperties>();

			for (Reservation res : foundRes) {

				ReservationProperties resProp = new ReservationProperties();

				resProp.reservationIdProp().setValue(Long.toString(res.getReservationId()));
				resProp.resNameProp().setValue(res.getResName());
				resProp.checkInProp().setValue(res.getCheckIn().toString());
				resProp.checkOutProp().setValue(res.getCheckOut().toString());
				resProp.roomNrProp().setValue(res.getRoom().getRoomNumber());

				Set<String> guestDetails = res.getGuest().stream().map(Guest::getDetails).collect(Collectors.toSet());
				Iterator<String> it = guestDetails.iterator();
				String details = "";

				while (it.hasNext()) {

					String detail = it.next();
					details += " " + detail;
				}

				resProp.guestsNameProp().setValue(details);

				foundResProp.add(resProp);

			}
			ObservableList<ReservationProperties> observableFoundResProp = FXCollections
					.observableArrayList(foundResProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ReservationOverviewController gC = fxmlLoader.<ReservationOverviewController>getController();
			gC.getTableView().setItems(observableFoundResProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Reservation overview");
			stage.show();

		}

		else if (!byResName.getText().isEmpty() && byResId.getText().isEmpty() && byCheckIn.getValue() == null
				&& byCheckOut.getValue() == null) {

			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Reservation> foundRes = ReservationPersistence.getInstance().findByResName(byResName.getText());
			List<ReservationProperties> foundResProp = new ArrayList<ReservationProperties>();

			for (Reservation res : foundRes) {

				ReservationProperties resProp = new ReservationProperties();

				resProp.reservationIdProp().setValue(Long.toString(res.getReservationId()));
				resProp.resNameProp().setValue(res.getResName());
				resProp.checkInProp().setValue(res.getCheckIn().toString());
				resProp.checkOutProp().setValue(res.getCheckOut().toString());
				resProp.roomNrProp().setValue(res.getRoom().getRoomNumber());

				Set<String> guestDetails = res.getGuest().stream().map(Guest::getDetails).collect(Collectors.toSet());
				Iterator<String> it = guestDetails.iterator();
				String details = "";

				while (it.hasNext()) {

					String detail = it.next();
					details += " " + detail;
				}

				resProp.guestsNameProp().setValue(details);

				foundResProp.add(resProp);

			}
			ObservableList<ReservationProperties> observableFoundResProp = FXCollections
					.observableArrayList(foundResProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ReservationOverviewController gC = fxmlLoader.<ReservationOverviewController>getController();
			gC.getTableView().setItems(observableFoundResProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Reservation overview");
			stage.show();

		} else if (byCheckIn.getValue() != null && byResName.getText().isEmpty() && byResId.getText().isEmpty()
				&& byCheckOut.getValue() == null) {

			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Reservation> foundRes = ReservationPersistence.getInstance().findByCheckIn(byCheckIn.getValue());
			List<ReservationProperties> foundResProp = new ArrayList<ReservationProperties>();

			for (Reservation res : foundRes) {

				ReservationProperties resProp = new ReservationProperties();

				resProp.reservationIdProp().setValue(Long.toString(res.getReservationId()));
				resProp.resNameProp().setValue(res.getResName());
				resProp.checkInProp().setValue(res.getCheckIn().toString());
				resProp.checkOutProp().setValue(res.getCheckOut().toString());
				resProp.roomNrProp().setValue(res.getRoom().getRoomNumber());

				Set<String> guestDetails = res.getGuest().stream().map(Guest::getDetails).collect(Collectors.toSet());
				Iterator<String> it = guestDetails.iterator();
				String details = "";

				while (it.hasNext()) {

					String detail = it.next();
					details += " " + detail;
				}

				resProp.guestsNameProp().setValue(details);

				foundResProp.add(resProp);

			}
			ObservableList<ReservationProperties> observableFoundResProp = FXCollections
					.observableArrayList(foundResProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

			try {
				// Parent parent = (Parent) fxmlLoader.load();
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ReservationOverviewController gC = fxmlLoader.<ReservationOverviewController>getController();
			gC.getTableView().setItems(observableFoundResProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Reservation overview");
			stage.show();

		} else if (byCheckOut.getValue() != null && byResName.getText().isEmpty() && byResId.getText().isEmpty()
				&& byCheckIn.getValue() == null) {

			primaryStage = (Stage) next.getScene().getWindow();
			primaryStage.close();
			List<Reservation> foundRes = ReservationPersistence.getInstance().findByCheckOut(byCheckOut.getValue());
			List<ReservationProperties> foundResProp = new ArrayList<ReservationProperties>();

			for (Reservation res : foundRes) {

				ReservationProperties resProp = new ReservationProperties();

				resProp.reservationIdProp().setValue(Long.toString(res.getReservationId()));
				resProp.resNameProp().setValue(res.getResName());
				resProp.checkInProp().setValue(res.getCheckIn().toString());
				resProp.checkOutProp().setValue(res.getCheckOut().toString());
				resProp.roomNrProp().setValue(res.getRoom().getRoomNumber());

				Set<String> guestDetails = res.getGuest().stream().map(Guest::getDetails).collect(Collectors.toSet());
				Iterator<String> it = guestDetails.iterator();
				String details = "";

				while (it.hasNext()) {

					String detail = it.next();
					details += " " + detail;
				}

				resProp.guestsNameProp().setValue(details);

				foundResProp.add(resProp);

			}
			ObservableList<ReservationProperties> observableFoundResProp = FXCollections
					.observableArrayList(foundResProp);

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			ReservationOverviewController gC = fxmlLoader.<ReservationOverviewController>getController();
			gC.getTableView().setItems(observableFoundResProp);

			Parent root = fxmlLoader.getRoot();
			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Reservation overview - by check-out");
			stage.show();

		} else {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText(null);
			alert.setContentText("Searching is possible only by using ONE filter!");

			alert.showAndWait();

		}
	}

	@FXML
	void showAllResButtonClicked(ActionEvent event) {

		primaryStage = (Stage) showAllResButton.getScene().getWindow();
		primaryStage.close();

		List<Reservation> allRes = ReservationPersistence.getInstance().getAllReservations();
		List<ReservationProperties> allResProp = new ArrayList<ReservationProperties>();

		for (Reservation res : allRes) {

			ReservationProperties resProp = new ReservationProperties();

			resProp.reservationIdProp.setValue(Long.toString(res.getReservationId()));
			resProp.resNameProp.setValue(res.getResName());
			resProp.checkInProp.setValue(res.getCheckIn().toString());
			resProp.checkOutProp.setValue(res.getCheckOut().toString());
			resProp.roomNrProp.setValue(res.getRoom().getRoomNumber());

			Set<String> guestDetails = res.getGuest().stream().map(Guest::getDetails).collect(Collectors.toSet());
			Iterator<String> it = guestDetails.iterator();
			String details = "";

			while (it.hasNext()) {

				String detail = it.next();
				details += " " + detail;
			}

			resProp.guestsNameProp().setValue(details);

			allResProp.add(resProp);

		}

		ObservableList<ReservationProperties> observableResProp = FXCollections.observableArrayList(allResProp);
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("ReservationOverview.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		ReservationOverviewController rC = fxmlLoader.<ReservationOverviewController>getController();

		rC.getTableView().setItems(observableResProp);

		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("Reservation overview");
		stage.show();
	}

}
