package at.wifi.kokanovic.bookingapp;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SearchGuestOrReservationController {
	@FXML
	private Button findReservationButton;

	@FXML
	private Button findGuestButton;

	@FXML
	private Button back;

	private Stage primaryStage;

	@FXML
	void findGuestButtonClicked(ActionEvent event) {
		primaryStage = (Stage) findGuestButton.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("FindGuest.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Find guest");
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void findReservationButtonClicked(ActionEvent event) {
		primaryStage = (Stage) findGuestButton.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("FindReservation.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Find reservation");
		stage.setResizable(false);
		stage.show();
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
}
