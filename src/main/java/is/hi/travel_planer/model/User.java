package is.hi.travel_planer.model;

import java.time.LocalDate;

public class User {
	private String name;
	private String email;
	private String ssn;
	private String phone;
	private int groupSize;
	private LocalDate departureDate;
	private LocalDate returnDate;
	private String location;
	private String destination;

	public User(String name, String email, String ssn, String phone, int groupSize, String location, LocalDate departureDate, String destination, LocalDate returnDate) {
		this.name = name;
		this.email = email;
		this.ssn = ssn;
		this.phone = phone;
		this.groupSize = groupSize;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.location = location;
		this.destination = destination;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPhone() { return this.phone; }
	public String getSSN() { return this.ssn; }
	public int getGroupSize() { return this.groupSize; }
	public String getLocation() { return this.location; }
	public String getDestination() { return this.destination; }
	public LocalDate getDepartureDate() { return this.departureDate; }
	public LocalDate getReturnDate() { return this.returnDate; }
	public Duration getTripDuration() {
		return new Duration(this.getDepartureDate(), this.getReturnDate());
	}

	public is.hi.flight_booking.application.User toFlightUser() {
		return new is.hi.flight_booking.application.User(this.ssn, this.name);
	}
}
