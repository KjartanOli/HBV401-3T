package is.hi.travel_planer.model;

import java.time.LocalDate;

public class LocationDate {
	private final String location;
	private final LocalDate date;

	public LocationDate(String location, LocalDate date) {
		this.location = location;
		this.date = date;
	}

	public String getLocation() { return this.location; }
	public LocalDate getDate() { return this.date; }
}
