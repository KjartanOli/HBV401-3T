package is.hi.hotel.interfaces;

import is.hi.hotel.entities.Booking;

import java.util.List;

public interface IBookingRepository {
    int createBooking(Booking booking);
    List<Booking> getAllBookings();
}
