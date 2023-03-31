package is.hi.travel_planer.view;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.util.Callback;;

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

	private PackageController packageController;

	@FXML
	private void initialize() {
		var user = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Reykjavík", LocalDate.of(2023, 4, 1),
			"Akureyri", LocalDate.of(2023, 4, 4)
		);

		packageController = new PackageController(
			user,
			new FlightControllerMock(),
			new HotelControllerMock(),
			new QueryMock()
		);

		flights.setCellFactory(lv -> new FlightCell());

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
