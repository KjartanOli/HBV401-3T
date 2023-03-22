package is.hi.travel_planer.model;

import is.hi.travel_planer.model.LocationDate;

public class User {
	private String name;
	private String email;
	private String ssn;
	private String phone;
	private int groupSize;
	private LocationDate departure;
	private LocationDate ret;

	public User(String name, String email, String ssn, String phone, int groupSize, LocationDate departure, LocationDate ret) {
		this.name = name;
		this.email = email;
		this.ssn = ssn;
		this.phone = phone;
		this.groupSize = groupSize;
		this.departure = departure;
		this.ret = ret;
	}

	public String getName() { return this.name; }
	public String getEmail() { return this.email; }
	public String getPhone() { return this.phone; }
	public String getSSN() { return this.ssn; }
	public int getGroupSize() { return this.groupSize; }
	public LocationDate getDeparture() { return this.departure; }
	public LocationDate getReturn() { return this.ret; }
	public String getLocation() { return this.departure.getLocation(); }
	public String getDestination() { return this.ret.getLocation(); }

	public is.hi.flight_booking.application.User toFlightUser() {
		return new is.hi.flight_booking.application.User(this.ssn, this.name);
	}
}
