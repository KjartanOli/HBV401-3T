package is.hi.hotel;

public class Room {
    private int roomID;
    private int accomodates;
    private boolean isBooked;
    private Dates date;

    public Room(int roomID, int accomodates, boolean isBooked, Dates date){
        this.roomID = roomID;
        this.accomodates = accomodates;
        this.isBooked = isBooked;
        this.date = date;
    }

    public int getRoomID() { return this.roomID; }
	public int getAccomedates() { return this.accomodates; }
	public boolean getIsBooked() { return this.isBooked; }
	public Dates getDate() { return this.date; }
}


