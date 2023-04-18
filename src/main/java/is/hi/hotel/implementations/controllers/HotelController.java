package is.hi.hotel.implementations.controllers;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.hotel.interfaces.IHotelRepository;
import java.sql.SQLException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class HotelController implements IHotelController {

    private final IHotelRepository _hotelRepository;

    public HotelController(IHotelRepository hotelRepository) {
        _hotelRepository = hotelRepository;
    }

    public List<Hotel> searchHotels(BookingDate dates, int adults, int children, String location) {
        var hotels = _hotelRepository.getAllHotels();
        // filter hotel list, filter the list in steps, if at any time the list becomes empty return the empty list
        if (location != null) {
            hotels = filterHotelsByLocation(hotels, location);
            if (hotels.size() == 0) {
                return hotels;
            }
        }
        if (dates != null) {
            hotels = filterHotelsByDates(hotels, dates);
            if (hotels.size() == 0) {
                return hotels;
            }
        }
        if (adults != 0 || children != 0) {
            hotels = filterHotelByCapacity(hotels, adults, children);
            if (hotels.size() == 0) {
                return hotels;
            }
        }
        return hotels;
    }

    public List<Hotel> getAllHotels() {
        return _hotelRepository.getAllHotels();
    }

	public int createHotel(Hotel hotel) throws BadInputException {
		return 0;
	}

	public int createHotel(String name, String location, List<Room> rooms) throws BadInputException {
		return 0;
	}

   /* public int createHotel(Hotel hotel) throws BadInputException {
        //Validate hotel
        validateCreateHotelRequest(hotel);
        return _hotelRepository.createHotel(hotel);
    }

    public int createHotel(String name, String location, List<Room> rooms) throws BadInputException {
        //Init hotel obj
        Hotel hotel = new Hotel(getNewHotelId(), name, location, rooms);
        //Validate hotel
        validateCreateHotelRequest(hotel);
        return _hotelRepository.createHotel(hotel);
    }
*/
    public Hotel getHotelById(int hotelId) throws NotFoundException, SQLException {
        return _hotelRepository.getHotelById(hotelId);
    }

    private List<Hotel> filterHotelsByLocation(List<Hotel> hotels, String location) {
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) { // Lists are iterable, nicer code to use item iteration syntax
            if (location.toLowerCase().equals(hotel.getLocation().toLowerCase())) {
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
    }

    private List<Hotel> filterHotelsByDates(List<Hotel> hotels, BookingDate bookingDate) {
        var filterDateRange = bookingDate.getDateRange(); //Get all dates in range of checkIn and checkout
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            roomLoop:
            for (Room room : hotel.getRooms()) { // give loop name for outer loop break
                var roomBookedDates = room.getBookedDates();
                for (LocalDate date : filterDateRange) {
                    // if a date from filterDateRange is not found in availableDates, break
                    if (roomBookedDates.stream().anyMatch(d -> d.equals(date))) {
                        break roomLoop;
                    }
                }
                // if execution arrives here, all dates from filterDateRange are found in availableDates
                // add hotel, since there is a room that has the date range available.
                filteredHotels.add(hotel);
				break;
            }
        }
        return filteredHotels;
    }

    private List<Hotel> filterHotelByCapacity(List<Hotel> hotels, int adults, int children) {
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            for (Room room : hotel.getRooms()) {
                if (room.getCapacity() >= (children + adults)) {
                    filteredHotels.add(hotel);
					break;
                }
            }
        }
        return filteredHotels;
    }

    private int getNewHotelId() {
        var max = 0;
        for (Hotel hotel : _hotelRepository.getAllHotels()) {
            var id = hotel.getHotelId();
            if (id >= max) {
                max = id;
            }
        }
        return max + 1;
    }

    private void validateCreateHotelRequest(Hotel hotel) throws BadInputException {
        var hotels = _hotelRepository.getAllHotels();
        // Check if id already exists
        for (Hotel _hotel : hotels) {
            if (_hotel.getHotelId() == (hotel.getHotelId())) {
                throw new BadInputException("Hotel with Id already exists");
            }
        }
        // capacity > 0
        for (Room room : hotel.getRooms()) {
            if (room.getCapacity() <= 0) {
                throw new BadInputException("Hotel includes room with 0 or less capacity");
            }
        }
        // Check if name already exists
        for (Hotel _hotel : hotels) {
            if (_hotel.getName().equals(hotel.getName())) {
                throw new BadInputException("Hotel with name already exists");
            }
        }
        // Rooms.size() > 0
        if (hotel.getRooms().size() <= 0) {
            throw new BadInputException("Hotel cannot have 0 or less rooms");
        }
    }
}
