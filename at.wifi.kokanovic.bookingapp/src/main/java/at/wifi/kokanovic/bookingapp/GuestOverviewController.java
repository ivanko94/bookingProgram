package at.wifi.kokanovic.bookingapp;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

public class GuestOverviewController implements Initializable {

	@FXML
	private Button back;

	@FXML
	private Button finish;

	@FXML
	private Button deleteButton;

	@FXML
	private TableView<GuestProperties> tableView;

	@FXML
	private TableColumn<GuestProperties, String> idColumn;

	@FXML
	private TableColumn<GuestProperties, String> firstNameColumn;

	@FXML
	private TableColumn<GuestProperties, String> lastNameColumn;

	@FXML
	private TableColumn<GuestProperties, String> dateOfBirthColumn;

	@FXML
	private TableColumn<GuestProperties, String> genderColumn;

	@FXML
	private TableColumn<GuestProperties, String> idCardNrColumn;

	@FXML
	private TableColumn<GuestProperties, String> nationalityColumn;

	@FXML
	private TableColumn<GuestProperties, String> streetColumn;

	@FXML
	private TableColumn<GuestProperties, String> cityColumn;

	@FXML
	private TableColumn<GuestProperties, String> zipColumn;

	@FXML
	private TableColumn<GuestProperties, String> countryColumn;

	private Stage primaryStage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		idColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("idNumberProp"));
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("firstNameProp"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("lastNameProp"));
		dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("dateOfBirthProp"));
		genderColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("genderProp"));
		idCardNrColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("idCardNumberProp"));
		nationalityColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("nationalityProp"));
		streetColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("streetProp"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("cityProp"));
		zipColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("zipProp"));
		countryColumn.setCellValueFactory(new PropertyValueFactory<GuestProperties, String>("countryProp"));

		firstNameColumn.setSortable(true);
		firstNameColumn.setSortType(SortType.ASCENDING);
		idColumn.setSortable(true);
		idColumn.setSortType(SortType.ASCENDING);
		lastNameColumn.setSortable(true);
		lastNameColumn.setSortType(SortType.ASCENDING);

		tableView.setEditable(true);

		firstNameColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		lastNameColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		dateOfBirthColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		genderColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		idCardNrColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		nationalityColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		streetColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		cityColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		zipColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());
		countryColumn.setCellFactory(TextFieldTableCell.<GuestProperties>forTableColumn());

		firstNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {

				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);
				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeFirstName(newValue, guestIdOfChangedValue);

				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setFirstNameProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});

		lastNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);
				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeLastName(newValue, guestIdOfChangedValue);

				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setLastNameProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});

		dateOfBirthColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String oldValue = event.getOldValue();
				SimpleStringProperty oldValueProp = new SimpleStringProperty(oldValue);

				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();
				try {

					LocalDate parsedDate = LocalDate.parse(newValue);

					if (parsedDate.isAfter(LocalDate.now())) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("Warning");
						alert.setHeaderText(null);
						alert.setContentText("The Date of birth cannot be a future date");

						alert.showAndWait();
						List<GuestProperties> currentGuestProp = tableView.getItems();

						for (GuestProperties guestProp : currentGuestProp) {
							if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
								guestProp.setDateOfBirthProp(oldValueProp);
							}
						}

						tableView.refresh();

					} else {
						GuestPersistence.getInstance().changeDateOfBirth(parsedDate, guestIdOfChangedValue);

						List<GuestProperties> currentGuestProp = tableView.getItems();
						for (GuestProperties guestProp : currentGuestProp) {
							if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
								guestProp.setDateOfBirthProp(newValueProp);
							}
						}
						ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
								.observableArrayList(currentGuestProp);

						tableView.setItems(observableGuestPropUpdated);

						tableView.refresh();
					}
				} catch (DateTimeParseException e) {

					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText(null);
					alert.setContentText("Wrong date format!");

					alert.showAndWait();
					List<GuestProperties> currentGuestProp = tableView.getItems();

					for (GuestProperties guestProp : currentGuestProp) {
						if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
							guestProp.setDateOfBirthProp(oldValueProp);
						}
					}

					tableView.refresh();
				}

			}
		});
		genderColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeGender(newValue, guestIdOfChangedValue);

				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setGenderProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		idCardNrColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeIdCardNumber(newValue, guestIdOfChangedValue);
				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setIdCardNumberProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		nationalityColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeNationality(newValue, guestIdOfChangedValue);
				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setNationalityProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		streetColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeStreet(newValue, guestIdOfChangedValue);

				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setStreetProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		cityColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeCity(newValue, guestIdOfChangedValue);
				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setCityProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		zipColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeZip(newValue, guestIdOfChangedValue);
				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setZipProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});
		countryColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GuestProperties, String>>() {

			@Override
			public void handle(CellEditEvent<GuestProperties, String> event) {
				String newValue = event.getNewValue();
				SimpleStringProperty newValueProp = new SimpleStringProperty(newValue);

				String guestIdOfChangedValue = event.getTableView().getItems().get(event.getTablePosition().getRow())
						.getIdNumberProp();

				GuestPersistence.getInstance().changeCountry(newValue, guestIdOfChangedValue);

				List<GuestProperties> currentGuestProp = tableView.getItems();
				for (GuestProperties guestProp : currentGuestProp) {
					if (guestProp.getIdNumberProp().equals(guestIdOfChangedValue)) {
						guestProp.setCountryProp(newValueProp);
					}
				}
				ObservableList<GuestProperties> observableGuestPropUpdated = FXCollections
						.observableArrayList(currentGuestProp);

				tableView.setItems(observableGuestPropUpdated);

				tableView.refresh();
			}
		});

	}

	public TableView<GuestProperties> getTableView() {
		return tableView;
	}

	public void setTableView(TableView<GuestProperties> tableView) {
		this.tableView = tableView;
	}

	/**
	 * synchronizes table view with data base after making changes
	 * @return List of all guests with property fields
	 */
	public List<GuestProperties> getUpdatedListAllGuestProp() {

		List<Guest> allGuestsUpdated = GuestPersistence.getInstance().getAllGuests();
		List<GuestProperties> allGuestPropUpdated = new ArrayList<GuestProperties>();

		for (Guest guest : allGuestsUpdated) {

			GuestProperties guestPropUpdated = new GuestProperties();

			guestPropUpdated.idNumberProp().setValue(Long.toString(guest.getIdNumber()));
			guestPropUpdated.firstNameProp().setValue(guest.getFirstName());
			guestPropUpdated.lastNameProp().setValue(guest.getLastName());
			guestPropUpdated.dateOfBirthProp().setValue(guest.getDateOfBirth().toString());
			guestPropUpdated.genderProp().setValue(guest.getGender());
			guestPropUpdated.idCardNumberProp().setValue(guest.getIdCardNumber());
			guestPropUpdated.streetProp().setValue(guest.getStreet());
			guestPropUpdated.cityProp().setValue(guest.getCity());
			guestPropUpdated.zipProp().setValue(guest.getZip());
			guestPropUpdated.countryProp().setValue(guest.getCountry());
			guestPropUpdated.nationalityProp().setValue(guest.getNationality());

			allGuestPropUpdated.add(guestPropUpdated);

		}
		return allGuestPropUpdated;
	}

	@FXML
	void deleteClicked(ActionEvent event) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation needed");
		alert.setHeaderText(null);
		alert.setContentText("Delete selected?");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {

			try {
				ObservableList<GuestProperties> selectedGuestsProp = tableView.getSelectionModel().getSelectedItems();

				for (GuestProperties guestProp : selectedGuestsProp) {

					String guestIdOfSelectedItems = guestProp.getIdNumberProp();

					GuestPersistence.getInstance().deleteGuest(Long.parseLong(guestIdOfSelectedItems));
				}

				tableView.getItems().removeAll(selectedGuestsProp);
			} catch (Exception e) {

				ObservableList<GuestProperties> selectedGuestsProp = tableView.getSelectionModel().getSelectedItems();

				for (GuestProperties guestProp : selectedGuestsProp) {

					String guestIdOfSelectedItems = guestProp.getIdNumberProp();

					GuestPersistence.getInstance().deleteGuest(Long.parseLong(guestIdOfSelectedItems));

				}
				tableView.getItems().removeAll(selectedGuestsProp);
			}
		} else {

			alert.close();
		}

	}

	@FXML
	void backClicked(ActionEvent event) {
		primaryStage = (Stage) back.getScene().getWindow();
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

}
