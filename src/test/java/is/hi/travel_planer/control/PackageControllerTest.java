import org.junit.*;
import static org.junit.Assert.*;

import java.time.LocalDate;

import is.hi.travel_planer.mock.HotelControllerMock;
import is.hi.travel_planer.mock.FlightControllerMock;

import is.hi.travel_planer.model.User;
import is.hi.travel_planer.control.PackageController;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.DayTourController;

import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.entities.Dates;

public class PackageControllerTest {
	PackageController controller;

	@Before
	public void setUp() {
		var user = new User(
			"John Doe", "john@example.org", "1234567890", "1234567", 2,
			"Reykjavík", LocalDate.of(2023, 3, 24),
			"Akureyri", LocalDate.of(2023, 3, 28));
		var hc = new HotelControllerMock();
		var dc = new DayTourController();
		var fc = new FlightControllerMock();
		controller = new PackageController(user, fc, hc, dc);

		var hotel = new Hotel(1, "Akureyri", 1, new Room(101, 4, false,
						new Dates(LocalDate.of(2023,3,12), LocalDate.of(2023,3,27))));
	}

	@After
	public void tearDown() {
		controller = null;
	}

	@Test
	public void test() {
		var a = controller.createPackages();
		System.out.println(a.size());
	}

	@Test
	public void test_Hotel(){

	}
}
