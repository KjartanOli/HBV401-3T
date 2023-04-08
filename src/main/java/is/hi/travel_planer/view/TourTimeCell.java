package is.hi.travel_planer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import is.hi.travel_planer.model.TourTime;

public class TourTimeCell extends ListCell<TourTime> {
	private Parent root ;
	private TourTimeCellController controller ;

	public TourTimeCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TourTimeCell.fxml"));
			root = loader.load();
			controller = loader.getController() ;
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	protected void updateItem(TourTime tourTime, boolean empty) {
		super.updateItem(tourTime, empty);
		if (empty || tourTime == null) {
			setGraphic(null);
		} else {
			controller.setTourTime(tourTime);
			setGraphic(root);
		}
	}
}
