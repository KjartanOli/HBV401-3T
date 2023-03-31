package is.hi.travel_planer.control;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Dates;
import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;

import is.hi.flight_booking.interfaces.FlightControllerInterface;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.processing.QueryInterface;

public class PackageController {
	private User user;
	private FlightControllerInterface flightController;
	private IHotelController hotelController;
	private QueryInterface QueryInterface;

	private Flight[] flights;
	private DayTourDetails[] tours;
	private Hotel[] hotels;

	private List<Flight> getFlights() {
		return this.flightController.searchFlights(
			user.getLocation(),
			user.getDestination(),
			user.getDepartureDate()
		);
	}

	private List<Hotel> getHotels() {
		return this.hotelController.searchHotels(
			user.getDepartureDate(),
			user.getGroupSize(),
			0,
			user.getDestination()
		);
	}

	private List<DayTourDetails> getTours() {
		return Arrays.asList(
			QueryInterface.searchTourDetails(
				user.getDestination(),
				user.getDepartureDate(),
				user.getReturnDate(),
				' ',
				null
			)
		);
	}

	public PackageController(
		User user,
		FlightControllerInterface flightController,
		IHotelController hotelController,
		QueryInterface QueryInterface
	) {
		this.user = user;
		this.flightController = flightController;
		this.hotelController = hotelController;
		this.QueryInterface = QueryInterface;
	}

	public List<TravelPackage> createPackages(Flight flight) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var hotel : this.getHotels()) {
			for (var tour : this.getTours()) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages.size() > 3 ? packages.subList(0, 3) : packages;
	}

	public List<TravelPackage> createPackages(Hotel hotel) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var flight : this.getFlights()) {
			for (var tour : this.getTours()) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages.size() > 3 ? packages.subList(0, 3) : packages;
	}

	/**
	@param tour The DayTour the user has selected
	@return The list of all packages that include the given tour
	 */
	public List<TravelPackage> createPackages(DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var flight : this.getFlights()) {
			for (var hotel : this.getHotels()) {
				var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
				packages.add(t);
			}
		}

		return packages.size() > 3 ? packages.subList(0, 3) : packages;
	}

	public List<TravelPackage> createPackages() {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		for (var flight : this.getFlights()) {
			for (var hotel : this.getHotels()) {
				for (var tour : this.getTours()) {
					var t = new TravelPackage(flight, hotel, tour, user.getTripDuration());
					packages.add(t);
				}
			}
		}

		return packages.size() > 3 ? packages.subList(0, 3) : packages;
	}
}
