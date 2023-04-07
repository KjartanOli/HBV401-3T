package is.hi.travel_planer.mock;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.hotel.interfaces.IHotelController;
import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

/**
 * Mocks the Hotel group's HotelController.
 * The HotelController class is the Hotel groups concrete implementation of the
 * IHotelController interface, which provides methods for searching for hotels.
 * This mock provides an implementation of IHotelController which does not
 * communicate with any databases, and returns stable data for testing purposes.
 */

public class HotelControllerMock implements IHotelController {
	public List<Hotel> searchHotels(BookingDate dates, int adults, int children, String location) {
		Hotel H1 = new Hotel(1, "H1", "Akureyri", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H2 = new Hotel(2, "H2", "Egilsstaðir", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H3 = new Hotel(3, "H3", "Vík", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H4 = new Hotel(4, "H4", "Akureyri", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H5 = new Hotel(5, "H5", "Ísafjörður", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H6 = new Hotel(6, "H6", "Siglufjörður", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		Hotel H7 = new Hotel(7, "H7", "Akureyri", Arrays.asList(new Room(1, 1, new ArrayList<LocalDate>(), 500)));

		List<Hotel> hotelList = new ArrayList<Hotel>();
		hotelList.add(H1);
		hotelList.add(H2);
		hotelList.add(H3);
		hotelList.add(H4);
		hotelList.add(H5);
		hotelList.add(H6);

		List<Hotel> hotelResults = new ArrayList<Hotel>();
		for (Hotel hotel : hotelList){
			if (hotel.getLocation().equals(location)
//				&& hotel.getRoom().getIsBooked() != true
//				&& hotel.getRoom().getAccomedates() >= adults+children
//				&& hotel.getRoom().getDate().getDateIn().equals(date)){
			){
				hotelResults.add(hotel);
			}
		}

		return hotelResults;
	}

	public List<Hotel> getAllHotels() {
		return null;
	}

	public int createHotel(Hotel hotel) throws BadInputException {
		return 0;
	}

	public int createHotel(String name, String location, List<Room> rooms) throws BadInputException {
		return 0;
	}

	public Hotel getHotelById(int HotelId) throws NotFoundException {
		return null;
	}
}
