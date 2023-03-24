package is.hi.travel_planer.mock;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import is.hi.hotel.interfaces.IHotelController;
import is.hi.hotel.entities.Hotel;

public class HotelControllerMock implements IHotelController {
	public List<Hotel> getHotels() { return new ArrayList<Hotel>(); }

	public List<Hotel> searchHotels(LocalDate date, int adults, int children, String location) {
		return new ArrayList<Hotel>();
	}
}
