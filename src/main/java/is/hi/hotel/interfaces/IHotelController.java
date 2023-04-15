package is.hi.hotel.interfaces;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface IHotelController {

    List<Hotel> searchHotels(BookingDate dates, int adults, int children, String location);

    List<Hotel> getAllHotels();

    int createHotel(Hotel hotel) throws BadInputException;

    int createHotel(String name, String location, List<Room> rooms) throws BadInputException;

    Hotel getHotelById(int HotelId) throws NotFoundException, SQLException;
}
