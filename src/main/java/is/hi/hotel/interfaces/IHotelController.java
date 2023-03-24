package is.hi.hotel.interfaces;

import java.util.List;
import java.time.LocalDate;

import is.hi.hotel.entities.Hotel;

public interface IHotelController {
	List<Hotel> searchHotels(LocalDate date, int adults, int children, String location);
	List<Hotel> getHotels();
}
