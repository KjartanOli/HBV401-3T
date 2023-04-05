package is.hi.hotel.entities;

import java.util.ArrayList;

public class User {
    private int userId;
    private String name;
    private String email;
    private ArrayList<Booking> bookings;

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.bookings = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Booking> getbookings() {
        return bookings;
    }

    public void setbookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }
}
