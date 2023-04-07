package is.hi.travel_planer.view;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.HBox;

import com.daytour.processing.DayTourDetails;

public class TourView extends HBox {
	@FXML
	private Label name;

	public TourView() {
		var loader = new FXMLLoader(getClass().getResource("/fxml/TourView.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public TourView(DayTourDetails tour) {
		this();
		this.setTour(tour);
	}

	public void setTour(DayTourDetails tour) {
		name.setText(tour.getName());
	}
}
