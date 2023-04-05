package is.hi.hotel.implementations.repositories;

import java.util.List;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

public class HotelRepository {
	public Hotel getHotelById(int hotelId) throws NotFoundException {
		return null;
	}
	public List<Hotel> getAllHotels() {
		return null;
	}
	public int createHotel(Hotel hotel) throws BadInputException {
		return 0;
	}
	public Room getRoom(int roomId) throws NotFoundException {
		return null;
	}
}
