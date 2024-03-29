package is.hi.travel_planer.view;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TourTime;
import is.hi.travel_planer.control.PackageController;
import is.hi.hotel.entities.Room;

public class DetailSelectionController {
	@FXML
	private SeatSelector seatSelector;

	@FXML
	private ListView<Room> roomSelector;

	@FXML
	private ListView<TourTime> tourTimeSelector;

	@FXML
	private Button next;

	private TravelPackage pkg;
	private User user;
	private PackageController packageController;

	public DetailSelectionController(TravelPackage pkg, PackageController packageController) {
		this.pkg = pkg;
		this.user = packageController.getUser();
		this.packageController = packageController;
	}

	@FXML
	private void initialize() {
		enableNextButton();

		seatSelector.setFlight(pkg.getFlight());
		seatSelector.setGroupSize(user.getGroupSize());
		seatSelector.addEventHandler(ActionEvent.ACTION, event -> {
				enableNextButton();
				event.consume();
			});

		roomSelector.getItems().setAll(pkg.getHotel().getRooms());
		roomSelector.setCellFactory(lv -> new RoomCell());
		roomSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		roomSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Room>() {
			@Override
			public void changed(ObservableValue<? extends Room> observable, Room oldValue, Room newValue) {
				enableNextButton();
			}
		});

		tourTimeSelector.getItems().setAll(TourTime.from(pkg.getTour(), user.getTripDuration()));
		tourTimeSelector.setCellFactory(lv -> new TourTimeCell());
		tourTimeSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TourTime>() {
			@Override
			public void changed(ObservableValue<? extends TourTime> observable, TourTime oldValue, TourTime newValue) {
				enableNextButton();
			}
		});
	}

	private List<Room> selectedRooms() {
		return roomSelector.getSelectionModel().getSelectedItems();
	}

	private TourTime selectedTime() {
		return tourTimeSelector.getSelectionModel().getSelectedItem();
	}

	private int selectedRoomCapacity() {
		int c = 0;
		for (var room : selectedRooms())
			c += room.getCapacity();

		return c;
	}

	private void enableNextButton() {
		if (seatSelector.getSelected().size() < user.getGroupSize()
			|| selectedRoomCapacity() < user.getGroupSize()
			|| selectedTime() == null)
			next.setDisable(true);
		else
			next.setDisable(false);
	}

	@FXML
	private void handleNext(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		var loader = new FXMLLoader(getClass().getResource("/fxml/PaymentConfirmationView.fxml"));
		loader.setControllerFactory(c -> new PaymentConfirmationController(pkg, packageController.getUser(), packageController, seatSelector.getSelected(), selectedRooms(), selectedTime()));
		var scene = new Scene(loader.load(), 1280, 900);
		stage.setScene(scene);
	}

	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_PackageView.fxml"));
		loader.setControllerFactory(c -> new PackageSelectionController(packageController.getUser()));
		var scene = new Scene(loader.load(), 1280, 900);
		stage.setScene(scene);
	}
}
