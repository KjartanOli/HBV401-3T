package is.hi.hotel.implementations.repositories;

import is.hi.hotel.entities.BookingDate;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;
import is.hi.hotel.implementations.controllers.HotelController;
import is.hi.hotel.implementations.db.DatabaseConnection;
import is.hi.hotel.interfaces.IHotelRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelRepository implements IHotelRepository {

    private DatabaseConnection databaseConnection;

    public HotelRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    private ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            result = databaseConnection.executeSQLQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    private int executeUpdate(String query) {
        int result = -1;
        try {
            result = databaseConnection.executeSQLUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    private ArrayList<LocalDate> getBookedDatesForRoom(int roomId) throws SQLException {
        var query = "SELECT BookingDate.* FROM BookingDate JOIN Booking ON Booking.bookingDateId = BookingDate.bookingDateId WHERE Booking.roomId = " + roomId;
        var rs = executeQuery(query);
        ArrayList<BookingDate> bookingDates = new ArrayList<>();
        while (rs.next()) {
            bookingDates.add(new BookingDate(rs.getInt("bookingDateId"), LocalDate.parse(rs.getString("dateInn")), LocalDate.parse(rs.getString("dateOut"))));
        }
        ArrayList<LocalDate> bookedDates = new ArrayList<>();
        for(BookingDate bookingDate : bookingDates) {
            var dateRange = bookingDate.getDateRange();
            bookedDates.addAll(dateRange);
        }
        return bookedDates;
    }

    private ArrayList<Room> getRoomsForHotel(int hotelId) throws SQLException {
        var query = "SELECT * FROM Room WHERE hotelId = " + hotelId;
        var rs = executeQuery(query);
        ArrayList<Room> rooms = new ArrayList<>();
        while (rs.next()) {
            rooms.add(new Room(rs.getInt("roomId"), rs.getInt("capacity"), null, rs.getInt("price")));
        }
        for (Room room : rooms) {
            room.setBookedDates(getBookedDatesForRoom(room.getRoomId()));
        }
        return rooms;
    }

    public Room getRoomById(int roomId) throws NotFoundException {
        try {
            var query = "SELECT * FROM Room WHERE roomId = " + roomId;
            var rs = executeQuery(query);
            if (rs == null || rs.next() == false) {
                throw new NotFoundException("No Room with id " + roomId);
            }
            Room room = new Room(rs.getInt("roomId"), rs.getInt("capacity"), null, rs.getInt("price"));
            room.setBookedDates(getBookedDatesForRoom(room.getRoomId()));
            return room;
        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
            throw new NotFoundException("Error occurred");
        }
    }

    public Hotel getHotelById(int hotelId) throws NotFoundException {
        try {
            var query = "SELECT * FROM Hotel WHERE hotelId = " + hotelId;
            var rs = executeQuery(query);
            if (rs == null || rs.next() == false) {
                throw new NotFoundException("No Hotel with id " + hotelId);
            }
            Hotel hotel = new Hotel(rs.getInt("hotelId"), rs.getString("name"), rs.getString("location"), null);
            var rooms = getRoomsForHotel(hotelId);
            hotel.setRooms(rooms);
            return hotel;
        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
            throw new NotFoundException("Error occurred");
        }

    }

    public List<Hotel> getAllHotels()  {
        var query = "SELECT * FROM Hotel";
        var rs = executeQuery(query);
        ArrayList<Hotel> hotels = new ArrayList<>();
        try {
            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getInt("hotelId"), rs.getString("name"), rs.getString("location"), null);
                hotels.add(hotel);
            }
            //Add room outside of result set loop, as adding the room requires another query
            //If we attempt to set the room inside the loop we will overwrite the previous result.
            for (Hotel hotel: hotels) {
                hotel.setRooms(getRoomsForHotel(hotel.getHotelId()));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotels;
    }

    public int createHotel(Hotel hotel) throws BadInputException {
        var query = "INSERT INTO Hotel(hotelId, name, location) VALUES('" + hotel.getHotelId()+"','"+hotel.getName()+"','"+hotel.getLocation()+"')";
        executeUpdate(query);
        Hotel resultHotel;
        try {
            resultHotel = getHotelById(hotel.getHotelId());
        } catch (NotFoundException e) {
            throw new BadInputException("Could not create hotel");
        }
        return resultHotel.getHotelId();
    }

    public int createRoom(Room room, int hotelId) throws BadInputException {
        var query = "INSERT INTO Room (roomId, capacity, price, hotelId) VALUES('" + room.getRoomId()+"','"+room.getCapacity()+"','"+room.getPrice() + "','" + hotelId + "')";
        executeUpdate(query);
        Room resultRoom;
        try {
            resultRoom = getRoomById(room.getRoomId());
        } catch (NotFoundException e) {
            throw new BadInputException("Could not create hotel");
        }
        return resultRoom.getRoomId();
    }
}
