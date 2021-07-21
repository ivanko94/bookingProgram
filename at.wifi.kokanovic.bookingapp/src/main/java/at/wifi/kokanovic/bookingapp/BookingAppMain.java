
package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BookingAppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		final String fxmlFileStart = "Start.fxml";
		final String cssFileStart = "Start.css";
		FXMLLoader loader = null;
		Parent root = null;

		try {
			loader = new FXMLLoader(getClass().getResource(fxmlFileStart));
			root = loader.load();
		} catch (LoadException e) {
			e.printStackTrace();
		} catch (IOException | NullPointerException e) {
			System.err.println("Could not load file: " + fxmlFileStart);
		}

		if (root != null) {
			((StartController) loader.getController()).setPrimaryStage(primaryStage);
			Scene scene = new Scene(root);

			URL cssFileWithPath = getClass().getResource(cssFileStart);
			if (cssFileWithPath == null) {
				System.err.println("Could not load file: " + cssFileStart);
			} else {
				scene.getStylesheets().add(cssFileWithPath.toExternalForm());
			}

			primaryStage.setTitle("Booking Application");
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(500);
			primaryStage.setMinHeight(350);
			primaryStage.setResizable(false);
			primaryStage.show();
		}

	}

	public static void main(String[] args) {

		launch(args);

	}
}
