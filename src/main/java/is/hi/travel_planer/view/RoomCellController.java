package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import is.hi.hotel.entities.Room;

public class RoomCellController {
	@FXML
	private Label id, capacity;

	public void setRoom(Room room) {
		id.setText(Integer.toString(room.getRoomId()));
		capacity.setText(Integer.toString(room.getCapacity()));
	}
}
