package is.hi.travel_planer.control;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalTime;
import java.time.LocalDate;

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
import com.daytour.processing.QueryInterface;

public class PackageController {
	private User user;
	private FlightControllerInterface flightController;
	private BookingControllerInterface flightBooking;
	private IHotelController hotelController;
	private IBookingController hotelBooking;
	private QueryInterface tourController;

	private List<Flight> flights;
	private List<DayTourDetails> tours;
	private List<Hotel> hotels;

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

	private void clearCache() {
		this.flights = null;
		this.hotels = null;
		this.tours = null;
	}

	public void setGroupSize(int groupSize) {
		this.clearCache();
		this.user.setGroupSize(groupSize);
	}

	public void setInterest(String interest) {
		this.clearCache();
		this.user.setInterest(interest);
	}

	public void setDestination(String destination) {
		this.clearCache();
		this.user.setDestination(destination);
	}

	public void setDepartureDate(LocalDate departureDate) {
		this.clearCache();
		this.user.setDepartureDate(departureDate);
	}

	public void setReturnDate(LocalDate returnDate) {
		this.clearCache();
		this.user.setReturnDate(returnDate);
	}

	public User getUser() {
		return this.user;
	}

	public List<Flight> getFlights() {
		if (this.flights == null) {
			this.flights = this.flightController.searchFlights(
				user.getOrigin(),
				user.getDestination(),
				user.getDepartureDate()
			);
		}
		return this.flights;
	}

	public List<Hotel> getHotels() {
		if (this.hotels == null) {
			System.err.printf("%s, %s, %s%n", user.getTripDuration(), user.getGroupSize(), user.getDestination());
			this.hotels = this.hotelController.searchHotels(
				user.getTripDuration(),
				user.getGroupSize(),
				0,
				user.getDestination()
			);
			for (var h : hotels)
				System.err.println(h);
		}
		return this.hotels;
	}

	public List<DayTourDetails> getTours() {
		if (this.tours == null) {
			this.tours =  Arrays.asList(
				tourController.searchTourDetails(
					user.getDestination(),
					user.getDepartureDate(),
					user.getReturnDate(),
					' ',
					user.getInterest()
				)
			);
		}
		return this.tours;
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
		Collections.shuffle(packages);
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
