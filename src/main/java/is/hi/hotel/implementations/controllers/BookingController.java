package is.hi.hotel.implementations.controllers;

import is.hi.hotel.entities.*;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.interfaces.IBookingController;
import is.hi.hotel.interfaces.IBookingRepository;
import java.time.LocalDate;

public class BookingController implements IBookingController{

    private final IBookingRepository _bookingRepository;

    public BookingController(IBookingRepository bookingRepository){
        _bookingRepository = bookingRepository;
    }

    public int createBooking(Booking booking) throws BadInputException {
        validateBooking(booking);
        return _bookingRepository.createBooking(booking);
    }

    public int createBooking(Room room, BookingDate dates, User user) throws BadInputException {
        var booking = new Booking(getNewBookingId(), room, user, dates);
        validateBooking(booking);
        return _bookingRepository.createBooking(booking);
    }

    private void validateBooking(Booking booking) throws BadInputException {
        // Dates valid
        var bookedDates = booking.getRoom().getBookedDates();
        for (LocalDate date : booking.getDates().getDateRange()) {
            // if a date from filterDateRange is not found in availableDates, break
            if (bookedDates.stream().anyMatch(d -> d.equals(date))) {
                throw new BadInputException("Room does not have the requested dates available");
            }
        }
    }

    private int getNewBookingId() {
        var max = 0;
        for (Booking booking: _bookingRepository.getAllBookings()){
            var id = booking.getBookingId();
            if (id >= max) {
                max = id;
            }
        }
        return max + 1;
    }
}
