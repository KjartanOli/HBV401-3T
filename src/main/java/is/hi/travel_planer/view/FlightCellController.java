package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import is.hi.flight_booking.application.Flight;

public class FlightCellController {
	@FXML
	private Label id;

	@FXML
	private Label price;

	@FXML
	private Label origin;

	@FXML
	private Label destination;

	public void setFlight(Flight flight) {
		id.setText(flight.getFlightId());
		price.setText(Integer.toString(flight.getPrice()));
		origin.setText(flight.getDepartureAddress());
		destination.setText(flight.getArrivalAddress());
	}
}
