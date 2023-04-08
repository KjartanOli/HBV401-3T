package is.hi.travel_planer.view;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;

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

	private TravelPackage pkg;
	private User user;
	private PackageController packageController;

	public DetailSelectionController(TravelPackage pkg, User user, PackageController packageController) {
		this.pkg = pkg;
		this.user = user;
		this.packageController = packageController;
	}

	@FXML
	private void initialize() {
		seatSelector.setFlight(pkg.getFlight());
		roomSelector.getItems().setAll(pkg.getHotel().getRooms());
		roomSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		roomSelector.setCellFactory(lv -> new RoomCell());
		tourTimeSelector.getItems().setAll(TourTime.from(pkg.getTour(), user.getTripDuration()));
		tourTimeSelector.setCellFactory(lv -> new TourTimeCell());
	}

	private List<Room> selectedRooms() {
		return roomSelector.getSelectionModel().getSelectedItems();
	}

	private TourTime selecteTime() {
		return tourTimeSelector.getSelectionModel().getSelectedItem();
	}

	private int selectedRoomCapacity() {
		int c = 0;
		for (var room : selectedRooms())
			c += room.getCapacity();

		return c;
	}
}
