package is.hi.hotel.entities;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDate {
    private int _bookingDateId;
    private LocalDate _checkInDate;
    private LocalDate _checkoutDate;

    public BookingDate(int bookingDateId, LocalDate checkInDate, LocalDate checkoutDate) throws IllegalArgumentException {
        //TODO: Validate that checkin date is before checkoutdate, and not the same
        _bookingDateId = bookingDateId;
        if (checkInDate == null || checkoutDate == null) {
            throw new IllegalArgumentException("Checkindate or checkoutDate are null");
        }

        // if checkout date is before checkindate
        if (checkoutDate.compareTo(checkInDate) < 0) {
            throw new IllegalArgumentException("Checkout date is before checkin date");
        }
        _checkInDate = checkInDate;
        _checkoutDate = checkoutDate;
    }

    public LocalDate getCheckInDate() {
        return _checkInDate;
    }

    public void setCheckInDate(LocalDate _checkInDate) {
        this._checkInDate = _checkInDate;
    }

    public LocalDate getCheckoutDate() {
        return _checkoutDate;
    }

    public void setCheckoutDate(LocalDate _checkoutDate) {
        this._checkoutDate = _checkoutDate;
    }

    public List<LocalDate> getDateRange() {
        // Get all dates between checkIn and checkOut date, excluding the checkOut date.
        return _checkInDate.datesUntil(_checkoutDate).collect(Collectors.toList());
    }

    public int getBookingDateId() {
        return _bookingDateId;
    }

    public void setBookingDateId(int _bookingDateId) {
        this._bookingDateId = _bookingDateId;
    }
}
