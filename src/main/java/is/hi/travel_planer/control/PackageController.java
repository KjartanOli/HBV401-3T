package is.hi.travel_planer.control;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalTime;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TourTime;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;
import com.daytour.processing.DayTourDetails;
import com.daytour.processing.DayTourTimes;
import com.daytour.processing.Booking;

import is.hi.flight_booking.interfaces.FlightControllerInterface;
import is.hi.flight_booking.interfaces.BookingControllerInterface;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.hotel.interfaces.IBookingController;
import is.hi.daytour.processing.QueryInterface;

public class PackageController {
	private User user;
	private FlightControllerInterface flightController;
	private BookingControllerInterface flightBooking;
	private IHotelController hotelController;
	private IBookingController hotelBooking;
	private QueryInterface tourController;

	private Flight[] flights;
	private DayTourDetails[] tours;
	private Hotel[] hotels;

	public PackageController(
		User user,
		FlightControllerInterface flightController,
		BookingControllerInterface flightBooking,
		IHotelController hotelController,
		IBookingController hotelBooking,
		QueryInterface tourController
	) {
		this.user = user;
		this.flightController = flightController;
		this.flightBooking = flightBooking;
		this.hotelController = hotelController;
		this.hotelBooking = hotelBooking;
		this.tourController = tourController;
	}

	public User getUser() {
		return this.user;
	}

	public List<Flight> getFlights() {
		return this.flightController.searchFlights(
			user.getOrigin(),
			user.getDestination(),
			user.getDepartureDate()
		);
	}

	public List<Hotel> getHotels() {
		return this.hotelController.searchHotels(
			user.getTripDuration(),
			user.getGroupSize(),
			0,
			user.getDestination()
		);
	}

	public List<DayTourDetails> getTours() {
		return Arrays.asList(
			tourController.searchTourDetails(
				user.getDestination(),
				user.getDepartureDate(),
				user.getReturnDate(),
				' ',
				user.getInterest()
			)
		);
	}

	public List<TravelPackage> createPackages(Flight flight) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var hotel : this.getHotels()) {
			for (var tour : this.getTours()) {
				var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
				packages.add(t);
			}
		}

		return choosePackages(packages);
	}

	public List<TravelPackage> createPackages(Hotel hotel) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var flight : this.getFlights()) {
			for (var tour : this.getTours()) {
				var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
				packages.add(t);
			}
		}

		return choosePackages(packages);
	}

	/**
	@param tour The DayTour the user has selected
	@return The list of all packages that include the given tour
	 */
	public List<TravelPackage> createPackages(DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();

		for (var flight : this.getFlights()) {
			for (var hotel : this.getHotels()) {
				var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
				packages.add(t);
			}
		}

		return choosePackages(packages);
	}

	public List<TravelPackage> createPackages(Flight flight, Hotel hotel) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		for (var tour : this.getTours()) {
			var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
			packages.add(t);
		}

		return choosePackages(packages);
	}

	public List<TravelPackage> createPackages(Flight flight, DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		for (var hotel : this.getHotels()) {
			var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
			packages.add(t);
		}

		return choosePackages(packages);
	}

	public List<TravelPackage> createPackages(Hotel hotel, DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		for (var flight : this.getFlights()) {
			var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
			packages.add(t);
		}

		return choosePackages(packages);
	}


	public List<TravelPackage> createPackages(Flight flight, Hotel hotel, DayTourDetails tour) {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
		packages.add(t);

		return choosePackages(packages);
	}

	public List<TravelPackage> createPackages() {
		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		for (var flight : this.getFlights()) {
			for (var hotel : this.getHotels()) {
				for (var tour : this.getTours()) {
					var t = new TravelPackage(flight, hotel, tour, user.getGroupSize());
					packages.add(t);
				}
			}
		}

		return choosePackages(packages);
	}

	private List<TravelPackage> choosePackages(List<TravelPackage> packages) {
		return packages.size() > 3 ? packages.subList(0, 3) : packages;
	}

	public void bookPackage(TravelPackage pkg, List<Seat> seats, List<Room> rooms, TourTime time) {
		bookFlight(user, pkg.getFlight(), seats);
		bookHotel(user, rooms);
		bookTour(user, pkg.getTour(), time);
	}

	private void bookFlight(User user, Flight flight, List<Seat> seats) {
		flightBooking.createBooking(flight, user.toFlightUser() , seats);
	}

	private void bookHotel(User user, List<Room> rooms) {
		for (var room : rooms) {
			try {
				hotelBooking.createBooking(room, user.getTripDuration(), user.toHotelUser());
			}
			catch (BadInputException e) {
				// Realistically it is the duty of the caller to ensure valid input is passed, as such this exception
				// should never be thrown.
				continue;
			}
		}
	}

	private void bookTour(User user, DayTourDetails tour, TourTime time) {
		var booking = new Booking(
			tour.getID(),
			user.getName(),
			user.getEmail(),
			time.getDate(),
			time.getTime(),
			user.getGroupSize(),
			false,
			"",
			0
		);

		tourController.addBooking(booking);
	}
}
