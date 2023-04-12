package is.hi.travel_planer.model;

import java.time.LocalDate;

import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.BookingDate;

public class TravelPackage {
	private final Flight flight;
	private final Hotel hotel;
	private final DayTourDetails tour;
	private final int groupSize;

	public TravelPackage(Flight flight, Hotel hotel, DayTourDetails tour, int groupSize) {
		this.flight = flight;
		this.hotel = hotel;
		this.tour = tour;
		this.groupSize = groupSize;
	}

	public Flight getFlight() { return this.flight; }
	public Hotel getHotel() { return this.hotel; }
	public DayTourDetails getTour() { return this.tour; }
	public int getPrice() {
		return (int)(1.1 * groupSize * (flight.getPrice() + hotel.getRooms().get(0).get_price() + tour.getPrice()));
	}

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
		return String.format("Package {%n\tflight: %s %s -> %s%n\thotel: %s%n\ttour: %s%n}", this.flight.getFlightId(), this.flight.getDepartureAddress(), this.flight.getArrivalAddress(), this.hotel.getHotelId(), this.tour);
	}
}
