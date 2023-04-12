package is.hi.travel_planer.view;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;

import is.hi.travel_planer.control.PackageController;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.BookingDate;
import is.hi.flight_booking.application.Flight;
import com.daytour.processing.DayTourDetails;

import is.hi.flight_booking.interfaces.FlightControllerInterface;
import is.hi.flight_booking.controller.FlightController;
import is.hi.hotel.interfaces.IHotelController;
import is.hi.daytour.processing.QueryInterface;

import is.hi.travel_planer.mock.HotelControllerMock;
import is.hi.travel_planer.mock.QueryMock;
import is.hi.travel_planer.mock.FlightControllerMock;

public class PackageSelectionController {
	@FXML
	private ListView<Flight> flights;
	@FXML
	private ListView<Hotel> hotels;
	@FXML
	private ListView<DayTourDetails> tours;
	@FXML
	private ChoiceBox<String> destination, interest;
	@FXML
	private ChoiceBox<Integer> groupSize;
	@FXML
	private DatePicker departureDate, returnDate;
	@FXML
	private HBox recommendations, selectedPackage;
	@FXML
	private Button next;

	private TravelPackage pkg;
	private PackageController packageController;

	public PackageSelectionController(User user) {
		String flightDB = "flights.db";
		pkg = null;
		packageController = new PackageController(
			user,
			new FlightController(flightDB),
			new is.hi.flight_booking.controller.BookingController(flightDB),
			new HotelControllerMock(),
			new is.hi.hotel.implementations.controllers.BookingController(new is.hi.hotel.implementations.repositories.BookingRepository()),
			new QueryMock()
		);
	}

	@FXML
	private void initialize() {
		returnDate.setValue(packageController.getUser().getReturnDate());
		departureDate.setValue(packageController.getUser().getDepartureDate());

		interest.getItems().addAll(UserController.getInterestList());
		interest.setValue(packageController.getUser().getInterest());

		destination.getItems().addAll(UserController.getPlacesList());
		destination.setValue(packageController.getUser().getDestination());

		groupSize.getItems().addAll(1,2,3,4,5,6); // set 6 til að byrja með
		groupSize.setValue(packageController.getUser().getGroupSize());

		generateRecomendations();

		flights.setCellFactory(lv -> new FlightCell());
		flights.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Flight>() {
			@Override
			public void changed(ObservableValue<? extends Flight> observable, Flight oldValue, Flight newValue) {
				pkg = new TravelPackage(newValue, getSelectedHotel(), getSelectedTour(), packageController.getUser().getGroupSize());
				generateRecomendations();
				updateSelectedPackageView();
			}
		});

		hotels.setCellFactory(lv -> new HotelCell());
		hotels.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Hotel>() {
			@Override
			public void changed(ObservableValue<? extends Hotel> observable, Hotel oldValue, Hotel newValue) {
				pkg = new TravelPackage(getSelectedFlight(), newValue, getSelectedTour(), packageController.getUser().getGroupSize());
				generateRecomendations();
				updateSelectedPackageView();
			}
		});

		tours.setCellFactory(lv -> new TourCell());
		tours.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<DayTourDetails>() {
			@Override
			public void changed(ObservableValue<? extends DayTourDetails> observable, DayTourDetails oldValue, DayTourDetails newValue) {
				pkg = new TravelPackage(getSelectedFlight(), getSelectedHotel(), newValue, packageController.getUser().getGroupSize());
				generateRecomendations();
				updateSelectedPackageView();
			}
		});

		setOptions();
	}

	private void generateRecomendations() {
		var flight = getSelectedFlight();
		var hotel = getSelectedHotel();
		var tour = getSelectedTour();

		List<TravelPackage> packages = new ArrayList<TravelPackage>();
		if (flight == null && hotel == null && tour == null) {
			packages = packageController.createPackages();
		}
		if (flight != null && hotel == null && tour == null) {
			packages = packageController.createPackages(flight);
		}
		if (flight == null && hotel != null && tour == null) {
			packages = packageController.createPackages(hotel);
		}
		if (flight == null && hotel == null && tour != null) {
			packages = packageController.createPackages(tour);
		}
		if (flight != null && hotel != null && tour == null) {
			packages = packageController.createPackages(flight, hotel);
		}
		if (flight != null && hotel == null && tour != null) {
			packages = packageController.createPackages(flight, tour);
		}
		if (flight == null && hotel != null && tour != null) {
			packages = packageController.createPackages(hotel, tour);
		}
		if (flight != null && hotel != null && tour != null) {
			packages = packageController.createPackages(flight, hotel, tour);
		}

		List<PackageView> t = new ArrayList<PackageView>();
		for (var p : packages) {
			var view = new PackageView(p);
			view.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
					pkg = view.getPackage();
					updateSelectedPackageView();
					event.consume();
				});
			t.add(view);
		}
		recommendations.getChildren().setAll(t);
	}

	@FXML
	private void handleDestinationSelection(ActionEvent event) {
		packageController.getUser().setDestination(destination.getValue());
		generateRecomendations();
		setOptions();
	}

	@FXML
	private void handleInterestSelection(ActionEvent event) {
		packageController.getUser().setInterest(interest.getValue());
		generateRecomendations();
	}

	@FXML
	private void handleGroupSizeSelection(ActionEvent event) {
		packageController.getUser().setGroupSize(groupSize.getValue().intValue());
		generateRecomendations();
		if (pkg != null)
			pkg = new TravelPackage(pkg.getFlight(), pkg.getHotel(), pkg.getTour(), packageController.getUser().getGroupSize());

		updateSelectedPackageView();
	}

	@FXML
	private void handleDepartureDateSelection(ActionEvent event) {
		packageController.getUser().setDepartureDate(departureDate.getValue());
		generateRecomendations();
	}

	@FXML
	private void handleReturnDateSelection(ActionEvent event) {
		packageController.getUser().setReturnDate(returnDate.getValue());
		generateRecomendations();
	}

	@FXML
	private void handleNext(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		var loader = new FXMLLoader(getClass().getResource("/fxml/DetailSelection.fxml"));
		loader.setControllerFactory(c -> new DetailSelectionController(pkg, packageController.getUser(), packageController));
		var scene = new Scene(loader.load(), 1280, 900);
		stage.setScene(scene);
	}

	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_InitialView.fxml"));

		var scene = new Scene(loader.load(), 1280, 900);
		stage.setScene(scene);
	}

	private Flight getSelectedFlight() {
		return flights.getSelectionModel().getSelectedItem();
	}

	private Hotel getSelectedHotel() {
		return hotels.getSelectionModel().getSelectedItem();
	}

	private DayTourDetails getSelectedTour() {
		return tours.getSelectionModel().getSelectedItem();
	}

	private void updateSelectedPackageView() {
		if (pkg == null)
			return;
		if (pkg.getFlight() == null || pkg.getHotel() == null || pkg.getTour() == null) {
			selectedPackage.getChildren().setAll();
			next.setDisable(true);
		}
		else {
			selectedPackage.getChildren().setAll(new PackageView(pkg));
			next.setDisable(false);
		}
	}

	private void setOptions() {
		setFlights();
		setHotels();
		setTours();
	}

	private void setFlights() {
		flights.setItems(FXCollections.observableArrayList(packageController.getFlights()));
	}

	private void setHotels() {
		hotels.setItems(FXCollections.observableArrayList(packageController.getHotels()));
	}

	private void setTours() {
		tours.setItems(FXCollections.observableArrayList(packageController.getTours()));
	}
}
