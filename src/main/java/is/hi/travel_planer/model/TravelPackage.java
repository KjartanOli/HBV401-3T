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

	public Flight getFlight() { return this.flight; }
	public Hotel getHotel() { return this.hotel; }
	public DayTourDetails getTour() { return this.tour; }

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (!(o instanceof TravelPackage)) {
			return false;
		}

		var p = (TravelPackage) o;

		return this.flight.equals(p.flight) && this.hotel.equals(p.hotel) && this.tour.equals(p.tour);
	}

	@Override
	public String toString() {
		return String.format("Package {%n\tflight: %s%n\thotel: %s%n\ttour: %s%n}", this.flight, this.hotel, this.tour);
	}
}
