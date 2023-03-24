package is.hi.travel_planer.control;

import java.util.List;
import java.util.ArrayList;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Dates;
import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;

import is.hi.flight_booking.controller.FlightInterface;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.DayTourController;

public class PackageController {
	private User user;
	private FlightInterface flightController;
	private IHotelController hotelController;
	private DayTourController dayTourController;

	private Flight[] flights;
	private DayTourDetails[] tours;
	private Hotel[] hotels;

	public PackageController(User user, FlightInterface flightController, IHotelController hotelController, DayTourController dayTourController) {
		this.user = user;
		this.hotelController = hotelController;
		this.dayTourController = dayTourController;
		this.flights = flightController.searchFlight(user.getLocation(), user.getDestination(), user.getDepartureDate());
		this.hotels = this.hotelController.searchHotels(user.getDepartureDate(), user.getGroupSize(), 0, user.getDestination()).toArray(new Hotel[0]);
		var t = dayTourController.searchTours(user.getDestination(), user.getDepartureDate(), user.getReturnDate());
		this.tours = new DayTourDetails[t.length];
		for (int i = 0; i < t.length; ++i) {
			this.tours[i] = (DayTourDetails) t[i];
		}
	}

	public List<TravelPackage> createPackages(Flight flight) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>(this.hotels.length * this.tours.length);

		for (var hotel : this.hotels) {
			for (var tour : this.tours) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages;
	}

	public List<TravelPackage> createPackages(Hotel hotel) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>(this.flights.length * this.tours.length);

		for (var flight : this.flights) {
			for (var tour : this.tours) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages;
	}

	/**
	@param tour The DayTour the user has selected
	@return The list of all packages that include the given tour
	 */
	public List<TravelPackage> createPackages(DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>(this.flights.length * this.hotels.length);

		for (var flight : this.flights) {
			for (var hotel : this.hotels) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages;
	}

	public List<TravelPackage> createPackages() {
		List<TravelPackage> packages = new ArrayList<TravelPackage>(this.flights.length * this.hotels.length * this.hotels.length);
		for (var flight : this.flights) {
			for (var hotel : this.hotels) {
				for (var tour : this.tours) {
					var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
					packages.add(t);
				}
			}
		}

		return packages;
	}
}
