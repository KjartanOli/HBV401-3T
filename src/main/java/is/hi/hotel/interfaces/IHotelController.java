package is.hi.hotel.interfaces;

import java.util.List;
import java.time.LocalDate;

import is.hi.hotel.entities.Hotel;

public interface IHotelController {
	/**
	 * Searches for Hotels matching a given criteria
	 * @param date The date of the customers arrival
	 * @param adults The number of adults in the customers group
	 * @param children The number of children in the customers group
	 * @param location The location at which the customer needs a hotel.
	 * @return All hotels in the specified location, with enough free rooms on the date of arrival to accomedate the group
	*/
	List<Hotel> searchHotels(LocalDate date, int adults, int children, String location);
}
