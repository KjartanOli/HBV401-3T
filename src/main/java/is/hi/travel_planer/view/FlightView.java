package is.hi.travel_planer.view;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.HBox;

import is.hi.flight_booking.application.Flight;

public class FlightView extends HBox {
	@FXML
	private Label id;
	@FXML
	private Label origin;
	@FXML
	private Label destination;

	public FlightView() {
		var loader = new FXMLLoader(getClass().getResource("/fxml/FlightView.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public FlightView(Flight flight) {
		this();
		this.setFlight(flight);
	}

	public void setFlight(Flight flight) {
		id.setText(flight.getFlightId());
		origin.setText(flight.getDepartureAddress());
		destination.setText(flight.getArrivalAddress());
	}
}
