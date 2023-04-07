package is.hi.travel_planer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import is.hi.hotel.entities.Hotel;

public class HotelCell extends ListCell<Hotel> {
	private Parent root ;
	private HotelCellController controller ;

	public HotelCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HotelCell.fxml"));
			root = loader.load();
			controller = loader.getController() ;
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	protected void updateItem(Hotel flight, boolean empty) {
		super.updateItem(flight, empty);
		if (empty || flight == null) {
			setGraphic(null);
		} else {
			controller.setHotel(flight);
			setGraphic(root);
		}
	}
}
