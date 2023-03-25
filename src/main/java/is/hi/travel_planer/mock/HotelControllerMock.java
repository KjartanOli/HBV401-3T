package is.hi.travel_planer.mock;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.hotel.interfaces.IHotelController;
import is.hi.hotel.entities.Dates;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;

public class HotelControllerMock implements IHotelController {
	public List<Hotel> getHotels() { return new ArrayList<Hotel>(); }

	public List<Hotel> searchHotels(LocalDate date, int adults, int children, String location) {
		Hotel H1 = new Hotel(1, "Akureyri", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,1), LocalDate.of(2023,4,8))));

		Hotel H2 = new Hotel(2, "Egilsstaðir", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,4,2), LocalDate.of(2023,4,9))));

		Hotel H3 = new Hotel(3, "Vík", 1, new Room(101, 4, false,
			new Dates(LocalDate.of(2023,3,12), LocalDate.of(2023,3,27))));

		Hotel H4 = new Hotel(4, "Akureyri", 1, new Room(101, 2, true,
			new Dates(LocalDate.of(2023,3,12), LocalDate.of(2023,3,27))));

		Hotel H5 = new Hotel(5, "Ísafjörður", 1, new Room(101, 6, true,
			new Dates(LocalDate.of(2023,3,12), LocalDate.of(2023,3,27))));

		Hotel H6 = new Hotel(6, "Siglufjörður", 1, new Room(101, 2, false,
			new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

		Hotel H7 = new Hotel(7, "Akureyri", 1, new Room(101, 2, false,
			new Dates(LocalDate.of(2023,4,1), LocalDate.of(2023,4,27))));

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
				&& hotel.getRoom().getIsBooked() != true
				&& hotel.getRoom().getAccomedates() >= adults+children
				&& hotel.getRoom().getDate().getDateIn().equals(date)){
				hotelResults.add(hotel);
			}
		}

		return hotelResults;
	}
}
