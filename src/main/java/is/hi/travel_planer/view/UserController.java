package is.hi.travel_planer.view;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;

import is.hi.travel_planer.model.User;

import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;
import java.util.List;
import java.util.ArrayList;

public class UserController {
	@FXML
	private Button submit;

	@FXML
	private TextField name;
	@FXML
	private TextField email;
	@FXML
	private TextField ssn;

	@FXML
	private ChoiceBox destination;

	@FXML
	private DatePicker departureDate;
	@FXML
	private DatePicker returnDate;

	@FXML
	private void initialize() {
		System.err.println("test");
	}
	@FXML
	private void submit(ActionEvent event) throws IOException {
		var user = new User(
			name.getText(),
			email.getText(),
			ssn.getText(),
			"1234567",
			2,
			"Reykjavík",
			departureDate.getValue(),
			"Akureyri",
			departureDate.getValue()
		);

		System.err.println(name.getText());
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_PackageView.fxml"));
		loader.setControllerFactory(c -> new PackageSelectionController(user));
		var scene = new Scene(loader.load(), 800, 800);
		stage.setScene(scene);
		stage.show();
	}
}
