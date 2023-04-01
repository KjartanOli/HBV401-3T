package is.hi.travel_planer.view;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.HBox;

import is.hi.hotel.entities.Hotel;

public class HotelView extends HBox {
	@FXML
	private Label name;

	public HotelView() {
		var loader = new FXMLLoader(getClass().getResource("/fxml/HotelView.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public HotelView(Hotel hotel) {
		this();
		this.setHotel(hotel);
	}

	public void setHotel(Hotel hotel) {
		name.setText(Integer.toString(hotel.getHotelID()));
	}
}
