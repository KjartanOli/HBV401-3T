package is.hi.hotel.interfaces;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

import java.util.List;
import java.util.Date;

public interface IHotelRepository {
    Hotel getHotelById(int hotelId) throws NotFoundException;
    List<Hotel> getAllHotels();
    int createHotel(Hotel hotel) throws BadInputException;
    Room getRoom(int roomId) throws NotFoundException;
}
