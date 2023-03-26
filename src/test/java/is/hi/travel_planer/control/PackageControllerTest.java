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
	PackageController controller_test1;
	PackageController controller_test2;
	PackageController controller_test4;
	User user_test1;
	User user_test2;
	List<TravelPackage> expected_test1;
	List<TravelPackage> expected_test2;
	List<TravelPackage> expected_test4;

	@Before
	public void setUp() {
		// test 1 setup
		user_test1 = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Reykjavík", LocalDate.of(2023, 4, 1),
			"Akureyri", LocalDate.of(2023, 4, 4));
		var hc_test1 = new HotelControllerMock();
		var dc_test1 = new QueryMock();
		var fc_test1 = new FlightControllerMock();
		controller_test1 = new PackageController(user_test1, fc_test1, hc_test1, dc_test1);

		expected_test1 = new ArrayList<TravelPackage>();
		var id_test1 = "F-100";
		ArrayList<Seat> seats_test1 = new ArrayList<>();
		seats_test1.add(new Seat("A-1", id_test1, false));
		seats_test1.add(new Seat("A-2", id_test1, false));
		seats_test1.add(new Seat("A-3", id_test1, false));
		seats_test1.add(new Seat("A-4", id_test1, false));
		seats_test1.add(new Seat("B-1", id_test1, false));
		seats_test1.add(new Seat("B-2", id_test1, false));
		seats_test1.add(new Seat("B-3", id_test1, false));
		seats_test1.add(new Seat("B-4", id_test1, false));
		var f_test1 = new Flight(id_test1, seats_test1, "Reykjavík", "Akureyri", LocalDate.of(2023, 4, 1),
			LocalDate.of(2023, 4, 1), 2000);

		var h_test1 = new Hotel(1, "Akureyri", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,1), LocalDate.of(2023,4,8))));

		var tours_test1 = new DayTourDetails[] {
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

		for (var t : tours_test1) {
			expected_test1.add(new TravelPackage(f_test1, h_test1, t, user_test1.getTripDuration()));
		}

		//test 2 setup
		user_test2 = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Keflavík", LocalDate.of(2023, 4, 2),
			"Egilsstaðir", LocalDate.of(2023, 4, 4));
		var hc_test2 = new HotelControllerMock();
		var dc_test2 = new QueryMock();
		var fc_test2 = new FlightControllerMock();
		controller_test2 = new PackageController(user_test2, fc_test2, hc_test2, dc_test2);

		var id1_test2 = "F-101";
		ArrayList<Seat> seats1_test2 = new ArrayList<>();
		seats1_test2.add(new Seat("A-1", id1_test2, false));
		seats1_test2.add(new Seat("A-2", id1_test2, false));
		seats1_test2.add(new Seat("A-3", id1_test2, false));
		seats1_test2.add(new Seat("A-4", id1_test2, false));
		seats1_test2.add(new Seat("B-1", id1_test2, false));
		seats1_test2.add(new Seat("B-2", id1_test2, false));
		seats1_test2.add(new Seat("B-3", id1_test2, false));
		seats1_test2.add(new Seat("B-4", id1_test2, false));
		var f1_test2 = new Flight(id1_test2, seats1_test2, "Keflavík", "Egilsstaðir", LocalDate.of(2023, 4, 2),
			LocalDate.of(2023, 4, 4), 2000);

		var id2_test2 = "F-108";
		ArrayList<Seat> seats2_test2 = new ArrayList<>();
		seats2_test2.add(new Seat("A-1", id2_test2, false));
		seats2_test2.add(new Seat("A-2", id2_test2, false));
		seats2_test2.add(new Seat("A-3", id2_test2, false));
		seats2_test2.add(new Seat("A-4", id2_test2, false));
		seats2_test2.add(new Seat("B-1", id2_test2, false));
		seats2_test2.add(new Seat("B-2", id2_test2, false));
		seats2_test2.add(new Seat("B-3", id2_test2, false));
		seats2_test2.add(new Seat("B-4", id2_test2, false));
		var f2_test2 = new Flight(id2_test2, seats2_test2, "Keflavík", "Egilsstaðir", LocalDate.of(2023, 4, 2),
			LocalDate.of(2023, 4, 2), 1000);

		var h_test2 = new Hotel(2, "Egilsstaðir", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,2), LocalDate.of(2023,4,9))));

		var t_test2 = new DayTourDetails(
				"3", "Austur", "Egilsstaðir",
				"Test3", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
		);

		expected_test2 = new ArrayList<TravelPackage>();
		expected_test2.add(new TravelPackage(f1_test2, h_test2, t_test2, user_test2.getTripDuration()));
		expected_test2.add(new TravelPackage(f2_test2, h_test2, t_test2, user_test2.getTripDuration()));

		// test 4 setup
		expected_test4 = new ArrayList<TravelPackage>();
		expected_test4.add(new TravelPackage(f1_test2, h_test2, t_test2, user_test2.getTripDuration()));
		expected_test4.add(new TravelPackage(f2_test2, h_test2, t_test2, user_test2.getTripDuration()));

		controller_test4 = new PackageController(user_test2, fc_test2, hc_test2, dc_test2);
	}

	@After
	public void tearDown() {
		user_test1 = null;
		controller_test1 = null;

		user_test2 = null;
		controller_test2 = null;

		controller_test4 = null;
	}

	@Test
	public void testCreatePackagesMoreThanThree() {
		var result = controller_test1.createPackages();
		assertEquals(expected_test1, result);
	}

	@Test
	public void testCreatePackagesLessThanThree() {
		var result = controller_test2.createPackages();
		assertEquals(expected_test2, result);
	}

	@Test
	public void testCreatePackagesSpecificHotel() {
		var hc = new HotelControllerMock();
		var dc = new QueryMock();
		var fc = new FlightControllerMock();
		controller_test1 = new PackageController(user_test1, fc, hc, dc);

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
			expected.add(new TravelPackage(f, h, t, user_test1.getTripDuration()));
		}

		var result = controller_test1.createPackages(h);

		assertEquals(expected, result);
	}

	@Test
	public void testCreataPackagesSpecificDayTour() {
		var t = new DayTourDetails(
				"3", "Austur", "Egilsstaðir",
				"Test3", "0", "1", "200", "", "20",
				"Test inc.", "", "", "10:00-20:00"
		);


		var result = controller_test4.createPackages(t);
		assertEquals(expected_test4, result);
	}
}
