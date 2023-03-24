package is.hi.hotel.entities;

public class Hotel {
	private int hotelID;
	private String location;
	private int numberOfRooms;
	private Room room;

	public Hotel(int hotelID, String location, int numberOfRooms, Room room){
		this.hotelID = hotelID;
		this.location = location;
		this.numberOfRooms = numberOfRooms;
		this.room = room;
	}

	public int getHotelID() { return this.hotelID; }
	public String getLocation() { return this.location; }
	public int getNumberOfRooms() { return this.numberOfRooms; }
	public Room getRoom() { return this.room; }
}