package is.hi.hotel.entities;
import is.hi.hotel.exceptions.BadInputException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingDate {
    private LocalDate _checkInDate;
    private LocalDate _checkoutDate;

    public BookingDate(LocalDate checkInDate, LocalDate checkoutDate) {
        //TODO: Validate that checkin date is before checkoutdate, and not the same
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

}
