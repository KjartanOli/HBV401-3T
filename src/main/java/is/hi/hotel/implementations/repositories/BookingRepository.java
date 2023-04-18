package is.hi.hotel.implementations.repositories;

import is.hi.hotel.entities.*;
import is.hi.hotel.exceptions.BadInputException;
import is.hi.hotel.exceptions.NotFoundException;
import is.hi.hotel.implementations.db.DatabaseConnection;
import is.hi.hotel.interfaces.IBookingRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingRepository implements IBookingRepository {
    private DatabaseConnection databaseConnection;

    private ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            result = databaseConnection.executeSQLQuery(query);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

    private int executeUpdate(String query) {
        int result = -1;
        try {
            result = databaseConnection.executeSQLUpdate(query);
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
        return result;
    }

    public BookingRepository(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public int createBookingDate(BookingDate bookingDate) throws BadInputException {
        var query = "INSERT INTO BookingDate(bookingDateId ,dateInn, dateOut) values ('" + bookingDate.getBookingDateId() + "', '" + bookingDate.getCheckInDate().toString() +"', '" + bookingDate.getCheckoutDate().toString() + "')";
        executeUpdate(query);
        BookingDate resultDate;
        try {
            resultDate = getBookingDateById(bookingDate.getBookingDateId());
        } catch (NotFoundException e) {
            throw new BadInputException("Error occurred");
        }
        return resultDate.getBookingDateId();
    }

    public int createBooking(Booking booking) throws BadInputException {
        var bookingDateId = createBookingDate(booking.getDates());
        var query = "INSERT INTO Booking (bookingId, userId, roomId, bookingDateId) VALUES('" + booking.getBookingId()+"','"+booking.getUser().getUserId()+"','"+booking.getRoom().getRoomId() + "','" + bookingDateId + "')";
        var res = executeUpdate(query);
        if (res != 1) {
            throw new BadInputException("Could not create Booking");
        }
        return booking.getBookingId();
    }

    public User getUserById(int userId) throws NotFoundException {
        try {
            var query = "SELECT * FROM User WHERE userId = " + userId;
            var rs = executeQuery(query);
            if (rs == null || rs.next() == false) {
                throw new NotFoundException("No user with id " + userId);
            }
            return new User(rs.getInt("userId"), rs.getString("name"), rs.getString("email"));
        } catch (Exception e) {
			//          System.out.print(Arrays.toString(e.getStackTrace()));
			throw new RuntimeException(e);
//            throw new NotFoundException("Error occurred");
        }
    }

    public int createUser(User user) throws BadInputException {
        var query = "INSERT INTO User(userId, name, email) values (" + user.getUserId() + ", '" + user.getName() + "', '" + user.getEmail() + "')";
        executeUpdate(query);
        User resultUser;
        try {
            resultUser = getUserById(user.getUserId());
        } catch (NotFoundException e) {
            throw new BadInputException("Error occurred");
        }
        return resultUser.getUserId();
    }


    public BookingDate getBookingDateById(int dateId) throws NotFoundException {
        try {
            var query = "SELECT * FROM BookingDate WHERE bookingDateId = " + dateId;
            var rs = executeQuery(query);
            if (rs == null || rs.next() == false) {
                throw new NotFoundException("No Booking Date with id " + dateId);
            }
            return new BookingDate(rs.getInt("bookingDateId"), LocalDate.parse(rs.getString("dateInn")), LocalDate.parse(rs.getString("dateOut")));
        } catch (Exception e) {
            System.out.print(Arrays.toString(e.getStackTrace()));
            System.out.print(e.getMessage());
            throw new NotFoundException("Error occurred");
        }
    }

    public List<Booking> getAllBookings() {
        var query = "SELECT * FROM Booking";
        var rs = executeQuery(query);
        ArrayList<Booking> bookings = new ArrayList<>();
        try {
            while (rs.next()) {
                int bookingId = rs.getInt("bookingId");
                int roomId = rs.getInt("roomId");
                int userId = rs.getInt("userId");
                int dateId = rs.getInt("bookingDateId");
                //get and set room values in controller
                Room room = new Room(roomId, 0, null, 0);
                // add temp objects with only ids
                User user = new User(userId, "", "");
                BookingDate bookingDate = new BookingDate(dateId, LocalDate.MAX, LocalDate.MAX);
                Booking booking = new Booking(bookingId, room, user, bookingDate);
                bookings.add(booking);
            }
            for (Booking booking: bookings) {
                //these values require separate queries thus we move them outside the initial result set loop.
                booking.setUser(getUserById(booking.getUser().getUserId()));
                booking.setDates(getBookingDateById(booking.getDates().getBookingDateId()));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return bookings;
    }

    public List<User> getAllUsers() {
        var query = "SELECT * FROM User";
        var rs = executeQuery(query);
        ArrayList<User> users = new ArrayList<>();
        try {
            while (rs.next()) {
                int userId = rs.getInt("userId");
                String name = rs.getString("name");
                String email = rs.getString("email");
                users.add(new User(userId, name, email));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return users;
    }

    public List<BookingDate> getAllBookingDates() {
        var query = "SELECT * FROM BookingDate";
        var rs = executeQuery(query);
        ArrayList<BookingDate> bookingDates = new ArrayList<>();
        try {
            while (rs.next()) {
                int dateId = rs.getInt("bookingDateId");
                LocalDate dateInn = LocalDate.parse(rs.getString("dateInn"));
                LocalDate dateOut = LocalDate.parse(rs.getString("dateInn"));
                bookingDates.add(new BookingDate(dateId, dateInn, dateOut));
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return bookingDates;
    }
}
