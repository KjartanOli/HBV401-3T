package is.hi.travel_planer.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import is.hi.flight_booking.application.Flight;

public class FlightCell extends ListCell<Flight> {
	private Parent root ;
	private FlightCellController controller ;

	public FlightCell() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FlightCell.fxml"));
			root = loader.load();
			controller = loader.getController() ;
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}

	@Override
	protected void updateItem(Flight flight, boolean empty) {
		super.updateItem(flight, empty);
		if (empty || flight == null) {
			setGraphic(null);
		} else {
			controller.setFlight(flight);
			setGraphic(root);
		}
	}
}
