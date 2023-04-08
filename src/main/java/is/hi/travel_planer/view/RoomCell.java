package is.hi.travel_planer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import is.hi.hotel.entities.Room;

public class RoomCell extends ListCell<Room> {
	private Parent root ;
	private RoomCellController controller ;

	public RoomCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RoomCell.fxml"));
			root = loader.load();
			controller = loader.getController() ;
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	protected void updateItem(Room room, boolean empty) {
		super.updateItem(room, empty);
		if (empty || room == null) {
			setGraphic(null);
		} else {
			controller.setRoom(room);
			setGraphic(root);
		}
	}
}
