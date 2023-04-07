package is.hi.travel_planer.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

import com.daytour.processing.DayTourDetails;
import com.daytour.processing.DayTourTimes;
import is.hi.hotel.entities.BookingDate;

public class TourTime {
	private final LocalDate date;
	private final LocalTime time;

	public TourTime(LocalDate date, LocalTime time) {
		this.date = date;
		this.time = time;
	}

	public LocalDate getDate() { return this.date; }
	public LocalTime getTime() { return this.time; }

	public static List<TourTime> from(DayTourDetails tour, BookingDate timeRange) {
		List<TourTime> times = new ArrayList<TourTime>();

		LocalDate date = timeRange.getCheckInDate();
		while (date.compareTo(timeRange.getCheckoutDate()) <= 0) {
			for (LocalTime time : tour.getTimes(date)) {
				times.add(new TourTime(date, time));
			}
			date = date.plusDays(1);
		}

		return times;
	}
}
