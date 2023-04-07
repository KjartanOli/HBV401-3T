package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TourTime;
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

	public DetailSelectionController(TravelPackage pkg, User user) {
		this.pkg = pkg;
		this.user = user;
	}

	@FXML
	private void initialize() {
		seatSelector.setFlight(pkg.getFlight());
		roomSelector.getItems().setAll(pkg.getHotel().getRooms());
		roomSelector.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		tourTimeSelector.getItems().setAll(TourTime.from(pkg.getTour(), user.getTripDuration()));
	}
}
