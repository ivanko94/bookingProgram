package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

public class ReservationOverviewController implements Initializable {

	@FXML
	private Button deleteButton;

	@FXML
	private Button back;

	@FXML
	private Button finish;

	@FXML
	private Button addGuestToResButton;

	@FXML
	private TableView<ReservationProperties> tableView;

	@FXML
	private TableColumn<ReservationProperties, String> idColumn;

	@FXML
	private TableColumn<ReservationProperties, String> nameColumn;

	@FXML
	private TableColumn<ReservationProperties, String> checkInColumn;

	@FXML
	private TableColumn<ReservationProperties, String> checkOutColumn;

	@FXML
	private TableColumn<ReservationProperties, String> roomColumn;

	@FXML
	private TableColumn<ReservationProperties, String> guestsColumn;

	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		idColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("reservationIdProp"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("resNameProp"));
		checkInColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("checkInProp"));
		checkOutColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("checkOutProp"));
		roomColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("roomNrProp"));
		guestsColumn.setCellValueFactory(new PropertyValueFactory<ReservationProperties, String>("guestsNameProp"));
		idColumn.setSortable(true);
		idColumn.setSortType(SortType.ASCENDING);
		nameColumn.setSortable(true);
		nameColumn.setSortType(SortType.ASCENDING);

		tableView.setEditable(true);

		nameColumn.setCellFactory(TextFieldTableCell.<ReservationProperties>forTableColumn());
		checkInColumn.setCellFactory(TextFieldTableCell.<ReservationProperties>forTableColumn());
		checkOutColumn.setCellFactory(TextFieldTableCell.<ReservationProperties>forTableColumn());
		roomColumn.setCellFactory(TextFieldTableCell.<ReservationProperties>forTableColumn());

		nameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ReservationProperties, String>>() {

			@Override
			public void handle(CellEditEvent<ReservationProperties, String> event) {

				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);
				String resIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getReservationIdProp();
				ReservationPersistence.getInstance().changeName(newValue, resIdOfChangedValue);

				List<ReservationProperties> currentResProp = tableView.getItems();
				for (ReservationProperties resProp : currentResProp) {
					if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
						resProp.setResNameProp(newValueProp);
					}
				}
				ObservableList<ReservationProperties> observableResPropUpdated = FXCollections
						.observableArrayList(currentResProp);

				tableView.setItems(observableResPropUpdated);

				tableView.refresh();
			}
		});

		checkInColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ReservationProperties, String>>() {

			@Override
			public void handle(CellEditEvent<ReservationProperties, String> event) {

				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);
				String oldValue = event.getOldValue();
				SimpleStringProperty oldValueProp = new SimpleStringProperty(oldValue);

				String resIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getReservationIdProp();

				try {
					LocalDate parsedCheckInDate = LocalDate.parse(newValue);
					LocalDate parsedCheckOutDate = LocalDate
							.parse(tableView.getSelectionModel().getSelectedItem().checkOutProp.getValue());

					if (parsedCheckInDate.isBefore(LocalDate.now())) {

						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("The check-in cannot be in the past");
						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckInProp(oldValueProp);
							}
						}

						tableView.refresh();

					}

					else if (parsedCheckInDate.isAfter(parsedCheckOutDate)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("The check-in cannot be after the check-out");

						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckInProp(oldValueProp);
							}
						}

						tableView.refresh();
					}

					else if (parsedCheckInDate.isEqual(parsedCheckOutDate)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Check-in date and check-out date cannot be the same!");
						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckInProp(oldValueProp);
							}
						}

						tableView.refresh();
					}

					else {
						ReservationPersistence.getInstance().changeCheckIn(parsedCheckInDate, resIdOfChangedValue);

						List<ReservationProperties> currentResProp = tableView.getItems();
						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckInProp(newValueProp);
							}
						}
						ObservableList<ReservationProperties> observableResPropUpdated = FXCollections
								.observableArrayList(currentResProp);

						tableView.setItems(observableResPropUpdated);

						tableView.refresh();
					}

				} catch (DateTimeParseException e) {

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Wrong date format!");

					alert.showAndWait();
					List<ReservationProperties> currentResProp = tableView.getItems();

					for (ReservationProperties resProp : currentResProp) {
						if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
							resProp.setCheckInProp(oldValueProp);
						}
					}

					tableView.refresh();
				}

			}
		});

		checkOutColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ReservationProperties, String>>() {

			@Override
			public void handle(CellEditEvent<ReservationProperties, String> event) {

				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);
				String oldValue = event.getOldValue();
				SimpleStringProperty oldValueProp = new SimpleStringProperty(oldValue);

				String resIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getReservationIdProp();

				try {
					LocalDate parsedCheckOutDate = LocalDate.parse(newValue);
					LocalDate parsedCheckInDate = LocalDate
							.parse(tableView.getSelectionModel().getSelectedItem().checkInProp.getValue());

					if (parsedCheckOutDate.isBefore(LocalDate.now())) {

						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("The check-out cannot be in the past");
						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckOutProp(oldValueProp);
							}
						}

						tableView.refresh();

					}

					else if (parsedCheckOutDate.isBefore(parsedCheckInDate)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("The check-out cannot be before the check-in");

						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckOutProp(oldValueProp);
							}
						}

						tableView.refresh();
					}

					else if (parsedCheckOutDate.isEqual(parsedCheckInDate)) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("Check-out date and check-in date cannot be the same!");
						alert.showAndWait();

						List<ReservationProperties> currentResProp = tableView.getItems();

						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckOutProp(oldValueProp);
							}
						}

						tableView.refresh();
					}

					else {
						ReservationPersistence.getInstance().changeCheckOut(parsedCheckOutDate, resIdOfChangedValue);

						List<ReservationProperties> currentResProp = tableView.getItems();
						for (ReservationProperties resProp : currentResProp) {
							if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
								resProp.setCheckOutProp(newValueProp);
							}
						}
						ObservableList<ReservationProperties> observableResPropUpdated = FXCollections
								.observableArrayList(currentResProp);

						tableView.setItems(observableResPropUpdated);

						tableView.refresh();
					}

				} catch (DateTimeParseException e) {

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Wrong date format!");

					alert.showAndWait();
					List<ReservationProperties> currentResProp = tableView.getItems();

					for (ReservationProperties resProp : currentResProp) {
						if (resProp.getReservationIdProp().equals(resIdOfChangedValue)) {
							resProp.setCheckOutProp(oldValueProp);
						}
					}

					tableView.refresh();
				}

			}
		});
	}

	@FXML
	void deleteClicked(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation needed");
		alert.setHeaderText(null);
		alert.setContentText("Delete selected?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {

			ObservableList<ReservationProperties> selectedResProp = tableView.getSelectionModel().getSelectedItems();

			for (ReservationProperties resProp : selectedResProp) {

				String resIdOfSelectedItems = resProp.getReservationIdProp();

				ReservationPersistence.getInstance().deleteRes(resIdOfSelectedItems);
			}

			tableView.getItems().removeAll(selectedResProp);
		} else {
			alert.close();
		}

	}

	@FXML
	void addGuestToResButtonClicked(ActionEvent event) {
		if (tableView.getSelectionModel().getSelectedItems().isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("No reservation selected!");

			alert.showAndWait();

		} else if (tableView.getSelectionModel().getSelectedItems().size() > 1) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning");
			alert.setHeaderText(null);
			alert.setContentText("Please select only one reservation!");

			alert.showAndWait();
		}

		else {
			primaryStage = (Stage) addGuestToResButton.getScene().getWindow();
			primaryStage.close();

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(getClass().getResource("AddGuestToRes.fxml"));

			try {
				fxmlLoader.load();
			} catch (IOException e) {
				e.printStackTrace();
			}

			AddGuestToResController newAddGuestToResController = fxmlLoader.getController();
			String idOfSelectedRes = tableView.getSelectionModel().getSelectedItem().getReservationIdProp();

			newAddGuestToResController.setResIdFromOverview(Long.parseLong(idOfSelectedRes));
			ObservableList<ReservationProperties> observableCurrentResProp = tableView.getItems();
			newAddGuestToResController.setObservableResPropFromOverview(observableCurrentResProp);

			Parent root = fxmlLoader.getRoot();

			Scene scene = new Scene(root);
			Stage stage = new Stage();

			stage.setScene(scene);
			stage.setTitle("Add guest to a reservation");
			stage.setResizable(false);
			stage.show();
		}

	}

	public boolean checkIfRoomIsOccupied() {

		List<Reservation> allRes = ReservationPersistence.getInstance().getAllReservations();
		boolean bull = false;
		String roomNrOfSelectedItem = tableView.getSelectionModel().getSelectedItem().roomNrProp.getValue();
		LocalDate parsedCheckInOfSelectedItem = LocalDate
				.parse(tableView.getSelectionModel().getSelectedItem().checkInProp.getValue());
		LocalDate parsedCheckOutOfSelectedItem = LocalDate
				.parse(tableView.getSelectionModel().getSelectedItem().checkOutProp.getValue());

		for (Reservation res : allRes) {

			if (!allRes.isEmpty()) {

				if (((roomNrOfSelectedItem.equals(res.getRoom().getRoomNumber()))
						&& (((parsedCheckInOfSelectedItem.isEqual(res.getCheckIn())
								&& parsedCheckOutOfSelectedItem.isEqual(res.getCheckOut()))
								|| (parsedCheckInOfSelectedItem.isEqual(res.getCheckIn()))
								|| (parsedCheckOutOfSelectedItem.isEqual(res.getCheckOut())))))) {

					bull = true;
					break;

				}
			} else {
				bull = false;
			}
		}
		return bull;

	}

	@FXML
	void backClicked(ActionEvent event) {
		primaryStage = (Stage) back.getScene().getWindow();
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
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	void finishClicked(ActionEvent event) {
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

	public TableView<ReservationProperties> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<ReservationProperties> tableView) {
		this.tableView = tableView;
	}

}
