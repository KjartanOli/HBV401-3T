package is.hi.travel_planer.model;

import java.time.LocalDate;

import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;
import is.hi.hotel.entities.Hotel;

public class TravelPackage {
	private final Flight flight;
	private final Hotel hotel;
	private final DayTourDetails tour;
	private final Duration duration;

	public TravelPackage(Flight flight, Hotel hotel, DayTourDetails tour, Duration duration) {
		this.flight = flight;
		this.hotel = hotel;
		this.tour = tour;
		this.duration = duration;
	}
}
