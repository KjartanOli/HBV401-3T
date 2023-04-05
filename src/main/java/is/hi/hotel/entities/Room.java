package is.hi.hotel.entities;

import java.time.LocalDate;
import java.util.List;

public class Room {
    private int _roomId;
    private int _capacity;
    private List<LocalDate> _bookedDates;
    private int _price;

    public Room(int roomId, int capacity, List<LocalDate> bookedDates, int price) {
        _roomId = roomId;
        _capacity = capacity;
        _bookedDates = bookedDates;
        _price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;

        if (this._roomId != room.getRoomId()){
            return false;
        }
        if (this._capacity != room.getCapacity()){
            return false;
        }
        if (!this._bookedDates.equals(room._bookedDates)) {
            return false;
        }
        return true;
    }

    public int getRoomId() {
        return _roomId;
    }

    public void setRoomId(int roomId) {
        this._roomId = roomId;
    }

    public int getCapacity() {
        return _capacity;
    }

    public void setCapacity(int capacity) {
        this._capacity = capacity;
    }

    public List<LocalDate> getBookedDates() {
        return _bookedDates;
    }

    public void setBookedDates(List<LocalDate> bookedDates) {
        this._bookedDates = bookedDates;
    }

    public int get_price() {
        return _price;
    }

    public void set_price(int price) {
        this._price = price;
    }
}
