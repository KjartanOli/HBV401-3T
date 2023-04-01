package is.hi.travel_planer.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.layout.VBox;

import is.hi.travel_planer.model.TravelPackage;

public class PackageView extends VBox {
	private TravelPackage pkg;

	@FXML
	private FlightView flight;
	@FXML
	private HotelView hotel;
	@FXML
	private TourView tour;

	public PackageView(TravelPackage pkg) {
		var loader = new FXMLLoader(getClass().getResource("/fxml/PackageView.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		this.pkg = pkg;
		this.flight.setFlight(pkg.getFlight());
		this.hotel.setHotel(pkg.getHotel());
		this.tour.setTour(pkg.getTour());
	}
}
