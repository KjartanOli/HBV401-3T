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
	/**
	 * Registers a new booking
	 * @param Booking The Booking to register
	*/
	public void addBooking(Booking booking);

	/**
	 * Searches for all tours matching the given criteria
	 * @param tour A string representing a tour's name, id, or location
	 * @param dateStart The start date of the search interval
	 * @param dateEnd The end date of the search interval
	 * @param order No idea what this is for
	 * @param filter A keyword/tag which the tours should match
	 * @return All tours whose name, id, or location match tour, which
        have a scheduled tour between dateStart and dateEnd.  If
        filter is not null, only return those tours which match
        filter.
	*/
	public DayTourDetails[] searchTourDetails(String tour, LocalDate dateStart, LocalDate dateEnd, char order, String filter);
}
