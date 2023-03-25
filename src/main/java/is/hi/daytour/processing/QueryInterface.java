package is.hi.daytour.processing;

import com.daytour.processing.Booking;
import com.daytour.processing.DayTourDetails;

import java.time.LocalDate;

/**
 * Interface for Query search class.
 *
 * @author Andri Fannar Kristjánsson, afk6@hi.is
 * @version 1.0
 * @since 2023-03-25
 **/
public interface QueryInterface
{
    public void addBooking(Booking booking);

    public DayTourDetails[] searchTourDetails(String tour, LocalDate dateStart, LocalDate dateEnd, char order, String filter);
}
