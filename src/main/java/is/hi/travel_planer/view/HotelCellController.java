package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import is.hi.hotel.entities.Hotel;

public class HotelCellController {
	@FXML
	private Label name;

	@FXML
	private Label price;

	public void setHotel(Hotel hotel) {
		name.setText(hotel.getName());
		price.setText(Integer.toString(hotel.getRooms().get(0).getPrice()));
	}
}
