package is.hi.travel_planer.mock;

import java.time.LocalDate;
import java.util.List;

import java.util.ArrayList;
import com.daytour.processing.DayTourDetails;
import com.daytour.processing.Booking;
import is.hi.daytour.processing.QueryInterface;

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
				"1", "Norð-Austur", "Akureyri",
				"Test", "0", "1", "100", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"2", "Norð-Austur", "Akureyri",
				"Test2", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"3", "Austur", "Egilsstaðir",
				"Test3", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"4", "Norð-Austur", "Akureyri",
				"Test4", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"5", "Norð-Austur", "Akureyri",
				"Test5", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
		};

		List<DayTourDetails> out = new ArrayList<DayTourDetails>();
		for (var t : a) {
			if (t.getTown().equals(tour)) {
				out.add(t);
			}
		}

		return out.toArray(new DayTourDetails[0]);
	}
}
