package is.hi.travel_planer.mock;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.flight_booking.controller.FlightInterface;
import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;

public class FlightControllerMock implements FlightInterface {
	public Flight[] searchFlight(String from, String destination, LocalDate date) {
		var ids = new String[] {"1", "2", "3"};
		var seats = new Seat[ids.length][5];
		for (int i = 0; i < ids.length; ++i) {
			for (int j = 0; j < 5; ++j) {
				seats[i][j] = new Seat(Integer.toString(j), ids[i], false);
			}
		}

		List<Flight> flights = new ArrayList<Flight>();
		// for (int i = 0; i < ids.length; ++i) {
		// 	flights.add(new Flight(id, seats[i], seats[i].length, 0, 500, ))
		// }

		return flights.toArray(new Flight[0]);
	}
}
