package is.hi.hotel.implementations.repositories;

import is.hi.hotel.entities.*;
import is.hi.hotel.implementations.db.DatabaseConnection;
import is.hi.hotel.interfaces.IBookingRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;



public class BookingRepository implements IBookingRepository {
    private final List<Booking> bookings;

    private DatabaseConnection databaseConnection;

    private ResultSet executeQuery(String query) {
        ResultSet result = null;
        try {
            result = databaseConnection.executeSQL(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public BookingRepository(DatabaseConnection databaseConnection) {
        //Tengja við gagnagrunn
        this.databaseConnection = databaseConnection;
        bookings = new ArrayList<>();
    }

    public int createBooking(Booking booking) {
        //Tengja við gagnagrunn

        //Bæta við línu í booking töflu í db
        return booking.getBookingId();
    }

    public List<Booking> getAllBookings() {
        var query = "SELECT * FROM Booking";
        var rs = executeQuery(query);
        ArrayList<Booking> bookings = new ArrayList<>();
                try {
                    while (rs.next()) {
                        int bookingId = rs.getInt("bookingId");
                        int roomId = rs.getInt("roomId");
                        String userId = rs.getString("userId");
                        String dateInn = rs.getString("dateInn");
                        String dateOut = rs.getString("dateOut");
                        Room room = new Room(roomId, 0, null, 0);
                        User user = new User(Integer.parseInt(userId), "", "");
                        BookingDate bookingDate = new BookingDate(LocalDate.parse(dateInn), LocalDate.parse(dateOut));
                        Booking booking = new Booking(bookingId, room, user, bookingDate);

                        bookings.add(booking);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                return bookings;
    }
}
