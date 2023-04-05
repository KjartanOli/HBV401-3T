package is.hi.hotel.implementations.repositories;

import is.hi.hotel.entities.Booking;
import is.hi.hotel.interfaces.IBookingRepository;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private final List<Booking> bookings;

    public BookingRepository() {
        // Tengja við gagnagrunn
        bookings = new ArrayList<>();
    }

    public int createBooking(Booking booking) {
        //Tengja við gagnagrunn
        //Bæta við línu í booking töflu í db
        return booking.getBookingId();
    }

    public List<Booking> getAllBookings() {
        //bookings = Bókanir í gagnagrunn
        return bookings;
    }

}
