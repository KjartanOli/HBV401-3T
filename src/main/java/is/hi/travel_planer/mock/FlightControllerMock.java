package is.hi.travel_planer.mock;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.flight_booking.interfaces.FlightControllerInterface;
import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;

public class FlightControllerMock implements FlightControllerInterface {
	public List<Flight> searchFlights(String from, String destination, LocalDate date) {
		String[] departures = {"Reykjavík", "Keflavík", "Húsavík", "Reykjavík", "Keflavík", "Keflavík", "Keflavík"};
		String[] destinations = {"Egilsstaðir", "Akureyri", "Vestmannaeyjar", "Egilsstaðir", "Akureyri", "Vestmannaeyjar", "Egilsstaðir"};
		List<Flight> flights = new ArrayList<>();
		for (int i = 0; i < departures.length; i++) {
			String id = "F-" + (100 + i);
			ArrayList<Seat> seats = new ArrayList<>();
			seats.add(new Seat("A-1", id, false));
			seats.add(new Seat("A-2", id, false));
			seats.add(new Seat("A-3", id, false));
			seats.add(new Seat("A-4", id, false));
			seats.add(new Seat("B-1", id, false));
			seats.add(new Seat("B-2", id, false));
			seats.add(new Seat("B-3", id, false));
			seats.add(new Seat("B-4", id, false));
			flights.add(
				new Flight(id, seats, departures[i], destinations[i], LocalDate.of(2023, 4, i + 1),
					LocalDate.of(2023, 4, i + 1), 1000 * (i + 2)));
		}

		return flights;
	}
}
