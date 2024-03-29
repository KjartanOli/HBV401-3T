package is.hi.travel_planer.mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.daytour.processing.DayTourDetails;
import com.daytour.processing.Booking;
import is.hi.daytour.processing.QueryInterface;

/**
 * Mocks the Day Tour group's Query/QueryController.
 * The Query class, is the Day Tour group's concrete implementation of
 * the QueryInterface which is responsible for searching for tours,
 * and registering bookings.  This mock provides an implementation of
 * the QueryInterface which does not communicate with any databases,
 * and returns stable data for testing purposes.
*/
public class QueryMock implements QueryInterface {
	public void addBooking(Booking booking) {
		//TODO
	}

	public DayTourDetails[] searchTourDetails(
		String tour,
		LocalDate dateStart,
		LocalDate dateEnd,
		char order,
		String filter
	) {
		var a = new DayTourDetails[] {
			new DayTourDetails(
				1, "Norð-Austur", "Akureyri",
				"Test", 0, 1, 100, "", 20,
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				2, "Norð-Austur", "Akureyri",
				"Test2", 0, 1, 200, "", 20,
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				3, "Austur", "Egilsstaðir",
				"Test3", 0, 1, 200, "", 20,
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				4, "Norð-Austur", "Akureyri",
				"Test4", 0, 1, 200, "", 20,
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				5, "Norð-Austur", "Akureyri",
				"Test5", 0, 1, 200, "", 20,
				"Test inc.", "", "", "10:00-20:00"
			),
		};

		List<LocalDate> dates = Arrays.asList(
			LocalDate.of(2023, 2, 5),
			LocalDate.of(2023, 2, 5),
			LocalDate.of(2023, 2, 6)
		);

		List<LocalTime> times = Arrays.asList(
			LocalTime.of(10, 0),
			LocalTime.of(11, 0),
			LocalTime.of(12, 0),
			LocalTime.of(13, 0)
		);

		for (var e : a) {
			for (var d : dates) {
				for (var t : times) {
					e.addTime(d, t, new Integer(5));
				}
			}
		}

		List<DayTourDetails> out = new ArrayList<DayTourDetails>();
		for (var t : a) {
			if (t.getTown().equals(tour)) {
				out.add(t);
			}
		}

		return out.toArray(new DayTourDetails[0]);
	}
}
