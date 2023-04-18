package is.hi.hotel.implementations.controllers;

import is.hi.hotel.entities.*;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;
import is.hi.hotel.interfaces.IBookingController;
import is.hi.hotel.interfaces.IBookingRepository;
import is.hi.hotel.interfaces.IHotelRepository;

import java.time.LocalDate;
import java.util.List;

public class BookingController implements IBookingController{

    private final IBookingRepository _bookingRepository;
    private final IHotelRepository _hotelRepository;

    public BookingController(IBookingRepository bookingRepository, IHotelRepository hotelRepository){
        _bookingRepository = bookingRepository;
        _hotelRepository = hotelRepository;
    }

    public int createBooking(Booking booking) throws BadInputException {
        validateBooking(booking);
        setBookingDateId(booking.getDates());
        return _bookingRepository.createBooking(booking);
    }

    public int createBooking(Room room, BookingDate dates, User user) throws BadInputException {
        var booking = new Booking(getNewBookingId(), room, user, dates);
        setBookingDateId(dates);
        validateBooking(booking);
        return _bookingRepository.createBooking(booking);
    }

    private void setBookingDateId(BookingDate dates) {
        if (dates.getBookingDateId() == -1) {
            dates.setBookingDateId(getNewBookingDateId());
        }
    }

    public User getUserById(int userId) throws NotFoundException {
        return _bookingRepository.getUserById(userId);
    }

    public List<Booking> getAllBookings() {
        var bookings = _bookingRepository.getAllBookings();
        for (Booking booking: bookings) {
            try {
                booking.setRoom(_hotelRepository.getRoomById(booking.getRoom().getRoomId()));
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return bookings;
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

    private int getNewBookingDateId() {
        var max = 0;
        for (BookingDate bookingDate: _bookingRepository.getAllBookingDates()){
            var id = bookingDate.getBookingDateId();
            if (id >= max) {
                max = id;
            }
        }
        return max + 1;
    }

}
