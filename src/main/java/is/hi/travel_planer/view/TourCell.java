package is.hi.travel_planer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import com.daytour.processing.DayTourDetails;

public class TourCell extends ListCell<DayTourDetails> {
	private Parent root ;
	private TourCellController controller ;

	public TourCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TourCell.fxml"));
			root = loader.load();
			controller = loader.getController() ;
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	protected void updateItem(DayTourDetails tour, boolean empty) {
		super.updateItem(tour, empty);
		if (empty || tour == null) {
			setGraphic(null);
		} else {
			controller.setTour(tour);
			setGraphic(root);
		}
	}
}
