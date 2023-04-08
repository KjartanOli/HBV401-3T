package is.hi.travel_planer.view;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import is.hi.travel_planer.model.TourTime;

public class TourTimeCellController {
	@FXML
	private Label date,time;

	public void setTourTime(TourTime tourTime) {
		date.setText(tourTime.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
		time.setText(tourTime.getTime().toString());
	}
}
