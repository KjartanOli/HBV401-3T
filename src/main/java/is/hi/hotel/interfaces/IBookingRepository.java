package is.hi.hotel.interfaces;

import is.hi.hotel.entities.Booking;
import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.User;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;

import java.util.List;

public interface IBookingRepository {
    int createBooking(Booking booking) throws BadInputException;
    List<Booking> getAllBookings();
    List<BookingDate> getAllBookingDates();
    User getUserById(int userId) throws NotFoundException;
}
