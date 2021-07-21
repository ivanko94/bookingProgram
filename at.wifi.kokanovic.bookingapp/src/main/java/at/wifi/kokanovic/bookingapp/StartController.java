package at.wifi.kokanovic.bookingapp;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController {

	@FXML
	private Button newResButton;
	@FXML
	private Button searchButton;

	@FXML
	private Button statisticButton;

	@FXML
	private Button exitButton;

	private Stage primaryStage;

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;

	}

	@FXML
	void newResButtonClicked(ActionEvent event) {

		primaryStage = (Stage) newResButton.getScene().getWindow();
		primaryStage.close();
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("Guest.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("New reservation");
		stage.setResizable(false);
		stage.show();

	}

	

	@FXML
	void searchClicked(ActionEvent event) {
		primaryStage = (Stage) searchButton.getScene().getWindow();
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
		stage.setTitle("Search");
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void showStatistic(ActionEvent event) {
		primaryStage = (Stage) statisticButton.getScene().getWindow();
		primaryStage.close();

		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("Statistic.fxml"));

		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Parent root = fxmlLoader.getRoot();
		Scene scene = new Scene(root);
		Stage stage = new Stage();

		stage.setScene(scene);
		stage.setTitle("Statistic");
		stage.setResizable(false);
		stage.show();
	}
	@FXML
	void exitTapped(ActionEvent event) {

		Platform.exit();
		
	}
}
