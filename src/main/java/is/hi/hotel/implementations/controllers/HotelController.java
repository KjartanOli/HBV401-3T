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
        hotels = filterHotelsByValidation(hotels);
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
        //Validate hotel
        validateCreateHotelRequest(hotel);
        try {
            return _hotelRepository.createHotel(hotel);
        } catch (Exception e) {
            throw new BadInputException(e.getMessage());
        }
    }

    public int createHotel(String name, String location, List<Room> rooms) throws BadInputException {
        //Init hotel obj
        Hotel hotel = new Hotel(getNewHotelId(), name, location, rooms);
        //Validate hotel
        validateCreateHotelRequest(hotel);
        for (Room room : rooms) {
            _hotelRepository.createRoom(room, hotel.getHotelId());
        }
        return _hotelRepository.createHotel(hotel);
    }

    public Hotel getHotelById(int hotelId) throws NotFoundException, SQLException {
        return _hotelRepository.getHotelById(hotelId);
    }

    public Room getRoomById(int roomId) throws NotFoundException {
        return _hotelRepository.getRoomById(roomId);
    }

    private List<Hotel> filterHotelsByValidation(List<Hotel> hotels) {
        // Filter out invalid hotels
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            var location = hotel.getLocation();
            var name = hotel.getName();
            if (hotel.getRooms().size() > 0 && name != null && !name.equals("") && location != null && !location.equals("")) {
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
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
        // filters hotels and their rooms by room availability
        var filterDateRange = bookingDate.getDateRange(); //Get all dates in range of checkIn and checkout
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            // rooms that fit filter criteria
            var filteredRooms = new ArrayList<Room>();
            roomLoop:
            for (Room room : hotel.getRooms()) {
                var roomBookedDates = room.getBookedDates();
                for (LocalDate date : filterDateRange) {
                    // if a date from filterDateRange is not found in availableDates, break
                    if (roomBookedDates.stream().anyMatch(d -> d.equals(date))) {
                        break roomLoop;
                    }
                }
                // add room to filteredRooms
                filteredRooms.add(room);
            }
            if (filteredRooms.size() > 0) {
                // set room list as rooms that fit filter criteria
                hotel.setRooms(filteredRooms);
                filteredHotels.add(hotel);
            }
        }
        return filteredHotels;
    }

    private List<Hotel> filterHotelByCapacity(List<Hotel> hotels, int adults, int children) {
        // filters hotels and their rooms by capacity
        var filteredHotels = new ArrayList<Hotel>();
        for (Hotel hotel : hotels) {
            // rooms that fit filter criteria
            var filteredRooms = new ArrayList<Room>();
            for (Room room : hotel.getRooms()) {
                if (room.getCapacity() >= (children + adults)) {
                    filteredRooms.add(room);
                }
            }
            if (filteredRooms.size() > 0){
                hotel.setRooms(filteredRooms);
                filteredHotels.add(hotel);
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
        if (hotel.getRooms() != null) {
            for (Room room : hotel.getRooms()) {
                if (room.getCapacity() <= 0) {
                    throw new BadInputException("Hotel includes room with 0 or less capacity");
                }
            }
        }

        // Check if name already exists
        for (Hotel _hotel : hotels) {
            if (_hotel.getName().equals(hotel.getName())) {
                throw new BadInputException("Hotel with name already exists");
            }
        }
        // Rooms.size() > 0
        if (hotel.getRooms() != null && hotel.getRooms().size() <= 0) {
            throw new BadInputException("Hotel cannot have 0 or less rooms");
        }
    }
}
