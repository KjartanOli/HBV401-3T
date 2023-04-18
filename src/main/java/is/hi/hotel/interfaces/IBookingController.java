package is.hi.hotel.interfaces;

import is.hi.hotel.entities.*;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

import java.util.List;

public interface IBookingController {
    int createBooking(Booking booking) throws BadInputException;
    int createBooking(Room room, BookingDate dates, User user) throws BadInputException;
    int createUser(String name, String email) throws BadInputException;
    List<Booking> getAllBookings();
    User getUserById(int userId) throws NotFoundException;
}
