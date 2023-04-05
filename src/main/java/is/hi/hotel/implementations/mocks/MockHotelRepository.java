package is.hi.hotel.implementations.mocks;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.NotFoundException;
import is.hi.hotel.interfaces.IHotelRepository;

import java.util.Date;
import java.util.List;

public class MockHotelRepository implements IHotelRepository {
    private final List<Hotel> _hotels;

    public MockHotelRepository(List<Hotel> hotels) {
        // Instantiate mock objects
        _hotels = hotels;
    }

    public List<Hotel> getAllHotels() {
        return _hotels;
    }

    public int createHotel(Hotel hotel) {
        _hotels.add(hotel);
        return hotel.getHotelId();
    }

    public Hotel getHotelById(int id) throws NotFoundException {
        for (Hotel hotel : _hotels) {
            if (hotel.getHotelId() == id){
                return hotel;
            }
        }
        throw new NotFoundException("Hotel with id " + id + "does not exist");
    }

	public Room getRoom(int roomId) throws NotFoundException {
		return null;
	}
}
