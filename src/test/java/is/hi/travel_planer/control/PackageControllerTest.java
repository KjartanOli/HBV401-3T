package is.hi.travel_planer.control;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.travel_planer.mock.HotelControllerMock;
import is.hi.travel_planer.mock.QueryMock;
import is.hi.travel_planer.mock.FlightControllerMock;

import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.control.PackageController;

import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.processing.QueryInterface;

import com.daytour.processing.DayTourDetails;

import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;

import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.entities.Dates;

public class PackageControllerTest {
	PackageController controller;
	User user;

	// @Before
	// public void setUp() {
	// }

	@After
	public void tearDown() {
		controller = null;
	}

	@Test
	public void testCreatePackagesMoreThanThree() {
		user = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Reykjavík", LocalDate.of(2023, 4, 1),
			"Akureyri", LocalDate.of(2023, 4, 4));
		var hc = new HotelControllerMock();
		var dc = new QueryMock();
		var fc = new FlightControllerMock();
		controller = new PackageController(user, fc, hc, dc);

		List<TravelPackage> expected = new ArrayList<TravelPackage>();
		var id = "F-100";
		ArrayList<Seat> seats = new ArrayList<>();
		seats.add(new Seat("A-1", id, false));
		seats.add(new Seat("A-2", id, false));
		seats.add(new Seat("A-3", id, false));
		seats.add(new Seat("A-4", id, false));
		seats.add(new Seat("B-1", id, false));
		seats.add(new Seat("B-2", id, false));
		seats.add(new Seat("B-3", id, false));
		seats.add(new Seat("B-4", id, false));
		var f = new Flight(id, seats, "Reykjavík", "Akureyri", LocalDate.of(2023, 4, 1),
			LocalDate.of(2023, 4, 1), 2000);

		var h = new Hotel(1, "Akureyri", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,1), LocalDate.of(2023,4,8))));

		var tours = new DayTourDetails[] {
			new DayTourDetails(
				"1", "Norð-Austur", "Akureyri",
				"Test", "0", "1", "100", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"2", "Norð-Austur", "Akureyri",
				"Test2", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"4", "Norð-Austur", "Akureyri",
				"Test4", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
		};

		for (var t : tours) {
			expected.add(new TravelPackage(f, h, t, user.getTripDuration()));
		}

		var result = controller.createPackages();
		assertEquals(expected, result);
	}

	@Test
	public void testCreatePackagesLessThanThree() {
		user = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Keflavík", LocalDate.of(2023, 4, 2),
			"Egilsstaðir", LocalDate.of(2023, 4, 4));
		var hc = new HotelControllerMock();
		var dc = new QueryMock();
		var fc = new FlightControllerMock();
		controller = new PackageController(user, fc, hc, dc);

		var id = "F-101";
		ArrayList<Seat> seats = new ArrayList<>();
		seats.add(new Seat("A-1", id, false));
		seats.add(new Seat("A-2", id, false));
		seats.add(new Seat("A-3", id, false));
		seats.add(new Seat("A-4", id, false));
		seats.add(new Seat("B-1", id, false));
		seats.add(new Seat("B-2", id, false));
		seats.add(new Seat("B-3", id, false));
		seats.add(new Seat("B-4", id, false));
		var f = new Flight(id, seats, "Keflavík", "Egilsstaðir", LocalDate.of(2023, 4, 2),
			LocalDate.of(2023, 4, 4), 2000);

		var h = new Hotel(2, "Egilsstaðir", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,2), LocalDate.of(2023,4,9))));

		var t = new DayTourDetails(
				"3", "Austur", "Egilsstaðir",
				"Test3", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
		);

		List<TravelPackage> expected = new ArrayList<TravelPackage>();
		expected.add(new TravelPackage(f, h, t, user.getTripDuration()));

		var result = controller.createPackages();

		assertEquals(expected, result);
	}

	@Test
	public void testCreatePackagesSpecificHotel() {
		user = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Reykjavík", LocalDate.of(2023, 4, 1),
			"Akureyri", LocalDate.of(2023, 4, 4));
		var hc = new HotelControllerMock();
		var dc = new QueryMock();
		var fc = new FlightControllerMock();
		controller = new PackageController(user, fc, hc, dc);

		var id = "F-100";
		ArrayList<Seat> seats = new ArrayList<>();
		seats.add(new Seat("A-1", id, false));
		seats.add(new Seat("A-2", id, false));
		seats.add(new Seat("A-3", id, false));
		seats.add(new Seat("A-4", id, false));
		seats.add(new Seat("B-1", id, false));
		seats.add(new Seat("B-2", id, false));
		seats.add(new Seat("B-3", id, false));
		seats.add(new Seat("B-4", id, false));
		var f = new Flight(id, seats, "Reykjavík", "Akureyri", LocalDate.of(2023, 4, 1),
			LocalDate.of(2023, 4, 4), 2000);

		var h = new Hotel(7, "Akureyri", 1, new Room(101, 2, false,
			new Dates(LocalDate.of(2023,4,1), LocalDate.of(2023,4,27))));

		List<TravelPackage> expected = new ArrayList<TravelPackage>();

		var tours = new DayTourDetails[] {
			new DayTourDetails(
				"1", "Norð-Austur", "Akureyri",
				"Test", "0", "1", "100", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"2", "Norð-Austur", "Akureyri",
				"Test2", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
			new DayTourDetails(
				"4", "Norð-Austur", "Akureyri",
				"Test4", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
			),
		};

		for (var t : tours) {
			expected.add(new TravelPackage(f, h, t, user.getTripDuration()));
		}

		var result = controller.createPackages(h);

		assertEquals(expected, result);
	}
}
