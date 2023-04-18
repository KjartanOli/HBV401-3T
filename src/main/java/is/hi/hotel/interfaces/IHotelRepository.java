package is.hi.hotel.interfaces;

import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface IHotelRepository {
    Hotel getHotelById(int hotelId) throws NotFoundException;

    List<Hotel> getAllHotels();

    int createHotel(Hotel hotel) throws BadInputException;

    int createRoom(Room room, int hotelId) throws BadInputException;

    Room getRoomById(int roomId) throws NotFoundException;
}
