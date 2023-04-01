package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import is.hi.travel_planer.control.PackageController;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Dates;
import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;

import is.hi.flight_booking.interfaces.FlightControllerInterface;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.processing.QueryInterface;

import is.hi.travel_planer.mock.HotelControllerMock;
import is.hi.travel_planer.mock.QueryMock;
import is.hi.travel_planer.mock.FlightControllerMock;

public class PackageSelectionController {
	@FXML
	private ListView<Flight> flights;

	@FXML
	private ListView<Hotel> hotels;

	@FXML
	private ListView<DayTourDetails> tours;

	@FXML
	private ChoiceBox destination;

	@FXML
	private DatePicker departureDate;
	@FXML
	private DatePicker returnDate;

	@FXML
	private HBox recommendations;

	private PackageController packageController;

	public PackageSelectionController(User user) {
		packageController = new PackageController(
			user,
			new FlightControllerMock(),
			new HotelControllerMock(),
			new QueryMock()
		);
	}

	@FXML
	private void initialize() {
		returnDate.setValue(packageController.getUser().getReturnDate());
		departureDate.setValue(packageController.getUser().getDepartureDate());

		for (var pkg : packageController.createPackages()) {
			recommendations.getChildren().add(new PackageView(pkg));
		}

		flights.setCellFactory(lv -> new FlightCell());
		tours.setCellFactory(lv -> new TourCell());

		for (var flight : packageController.getFlights()) {
			flights.getItems().add(flight);
		}

		for (var hotel : packageController.getHotels()) {
			hotels.getItems().add(hotel);
		}

		for (var tour : packageController.getTours()) {
			tours.getItems().add(tour);
		}
	}
}
