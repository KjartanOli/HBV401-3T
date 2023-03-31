package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.daytour.processing.DayTourDetails;

public class TourCellController {
	@FXML
	private Label title;

	@FXML
	private Label company;

	@FXML
	private Label price;

	@FXML
	private Label rating;

	public void setTour(DayTourDetails tour) {
		title.setText(tour.getName());
		price.setText(Integer.toString(tour.getPrice()));
		company.setText(tour.getCompany());
		rating.setText(String.format("%.1f", tour.getRating()));
	}
}
