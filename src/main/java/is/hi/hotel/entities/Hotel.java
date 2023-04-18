package is.hi.hotel.entities;

import java.util.List;

public class Hotel {
    private int hotelId;
    private String name;
    private String location;
    private List<Room> rooms;

    public Hotel(int hotelId,String name, String location, List<Room> rooms) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.rooms = rooms;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Hotel)) {
            return false;
        }
        Hotel hotel = (Hotel) o;

        if (this.hotelId != hotel.hotelId) {
            return false;
        }
        if (!this.name.equals(hotel.name)) {
            return false;
        }
        if (!this.location.equals(hotel.location)) {
            return false;
        }
        if (this.rooms.size() != hotel.getRooms().size()) {
            return false;
        }
        for (int i = 0; i < this.rooms.size(); i++) {
            if (!this.rooms.get(i).equals(hotel.getRooms().get(i))){
                return false;
            }
        }
        return true;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return this.name + "    " + this.location + "  " + findCheapestRoomPrice() + "kr";
    }

    private int findCheapestRoomPrice() {
        int min = 0;
        for(Room room : this.rooms) {
            if (room.getPrice() > min){
                min = room.getPrice();
            }
        }
        return min;
    }
}
