package is.hi.hotel.entities;

public class Booking {
    private int bookingId;
    private Room room;
    private User user;
    private BookingDate dates;

    public Booking(int bookingId, Room room, User user, BookingDate dates) {
        this.room = room;
        this.user = user;
        this.bookingId = bookingId;
        this.dates = dates;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public BookingDate getDates() {
        return dates;
    }

    public void setDates(BookingDate dates) {
        this.dates = dates;
    }
}
