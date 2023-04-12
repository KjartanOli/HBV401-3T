package is.hi.travel_planer.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TourTime;
import is.hi.travel_planer.control.PackageController;
import is.hi.hotel.entities.Room;
import is.hi.flight_booking.application.Seat;


public class PaymentConfirmationController {
	@FXML
	private TextField ccname, ccnr;
	@FXML
	private ChoiceBox<String> month;
	@FXML
	private ChoiceBox<Integer> year;
	@FXML
	private HBox selectedPackage;
	@FXML
	private Button finish;


	private TravelPackage pkg;
	private User user;
	private PackageController packageController;
	private List<Seat> seats;
	private List<Room> rooms;
	private TourTime tourTime;

	public PaymentConfirmationController(TravelPackage pkg, User user, PackageController packageController, List<Seat> seats, List<Room> rooms, TourTime tourTime) {
		this.pkg = pkg;
		this.user = user;
		this.packageController = packageController;
		this.seats = seats;
		this.rooms = rooms;
		this.tourTime = tourTime;
	}

	@FXML
	private void initialize() {
		month.getItems().addAll("janúar","febrúar","mars","apríl","maí","júní","júlí","ágúst","september","október","nóvember","desember");
		year.getItems().addAll(2022,2023,2024,2025,2026,2027,2028,2029,2030);

		selectedPackage.getChildren().setAll(new PackageView(pkg));

		finish.setDisable(true);

		ccname.textProperty().addListener((observable, oldValue, newValue) -> {
			enableConfirm(ccname, ccnr, month, year, finish);
		});
		
		ccnr.textProperty().addListener((observable, oldValue, newValue) -> {
			enableConfirm(ccname, ccnr, month, year, finish);
		});

		month.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			enableConfirm(ccname, ccnr, month, year, finish);
		});

		year.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			enableConfirm(ccname, ccnr, month, year, finish);
		});
	}

	private void enableConfirm(TextField ccname, TextField ccnr, ChoiceBox<String> month, ChoiceBox<Integer> year, Button finish) {
		if (!ccname.getText().isEmpty() && !ccnr.getText().isEmpty() && month.getValue() != null && year.getValue() != null) {
			finish.setDisable(false);
		} else {
			finish.setDisable(true);
		}
	}

	@FXML
	private void handleConfirm() {
		packageController.bookPackage(pkg, this.seats, this.rooms, this.tourTime);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Greiðsla móttekin");
		alert.setHeaderText(null);
		alert.setContentText("Bókun lokið. Staðfesting hefur verið send með tölvupósti.");
		alert.setOnHidden(event -> {
				System.exit(0);
			});
		alert.showAndWait();
	}
}
