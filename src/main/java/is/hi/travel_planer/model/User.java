package is.hi.travel_planer.model;

import java.time.LocalDate;

import is.hi.hotel.entities.BookingDate;

public class User {
	private String name;
	private String email;
	private String ssn;
	private String phone;
	private int groupSize;
	private LocalDate departureDate;
	private LocalDate returnDate;
	private String origin;
	private String destination;
	private String interest;
	private int maxPrice;

	public User(String name, String email, String ssn, String phone, int groupSize, String origin, 
				LocalDate departureDate, String destination, LocalDate returnDate, String interest, int maxPrice) {
		this.name = name;
		this.email = email;
		this.ssn = ssn;
		this.phone = phone;
		this.groupSize = groupSize;
		this.departureDate = departureDate;
		this.returnDate = returnDate;
		this.origin = origin;
		this.destination = destination;
		this.interest = interest;
		this.maxPrice = maxPrice;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPhone() { return this.phone; }
	public String getSSN() { return this.ssn; }
	public int getGroupSize() { return this.groupSize; }
	public String getOrigin() { return this.origin; }
	public String getDestination() { return this.destination; }
	public LocalDate getDepartureDate() { return this.departureDate; }
	public LocalDate getReturnDate() { return this.returnDate; }
	public BookingDate getTripDuration() {
		return new BookingDate(this.getDepartureDate(), this.getReturnDate());
	}
	public String getInterest() {return this.interest; }
	public int getMaxPrice() {return this.maxPrice; }


	public void setGroupSize(int groupSize){ this.groupSize = groupSize; }
	public void setInterest(String interest){ this.interest = interest; }
	public void setDestination(String destination){ this.destination = destination; }
	public void setDepartureDate(LocalDate departureDate){ this.departureDate = departureDate; }
	public void setReturnDate(LocalDate returnDate){ this.returnDate = returnDate; }
	public void setMaxPrice(int maxPrice){ this.maxPrice = maxPrice; }

	public is.hi.flight_booking.application.User toFlightUser() {
		return new is.hi.flight_booking.application.User(this.ssn, this.name);
	}

	public is.hi.hotel.entities.User toHotelUser() {
		// I have no idea what the id scheme is, so all users will be
		// user 0.
		return new is.hi.hotel.entities.User(0, this.name, this.email);
	}
}
