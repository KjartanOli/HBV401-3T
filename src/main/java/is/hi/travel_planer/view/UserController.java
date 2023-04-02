package is.hi.travel_planer.view;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
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
import java.util.Arrays;

public class UserController {
	@FXML
	private Button submit;
	@FXML
	private TextField name;
	@FXML
	private TextField ssn;
	@FXML
	private TextField email;
	@FXML
	private TextField phone;
	@FXML
	private ChoiceBox<String> interest;
	@FXML
	private ChoiceBox<Integer> people;
	@FXML
	private ChoiceBox<String> departure;
	@FXML
	private ChoiceBox<String> destination;
	@FXML
	private DatePicker departureDate;
	@FXML
	private DatePicker returnDate;
	@FXML
	private ChoiceBox<Integer> priceLow;
	@FXML
	private ChoiceBox<Integer> priceHigh;
	
	private static List<String> placesList = Arrays.asList(
		"Reykjavík", "Akureyri", "Egilsstaðir", "Húsavík", "Vík", "Keflavík", "Sauðárkrókur", "Stykkishólmur", "Ísafjörður"
	); // man ekki alla staðina, bætum þeim við
	private static List<String> interestList = Arrays.asList(
		"Fjölskylduvænt", "Upplifun/Ævintýri", "Bátur", "Safn", "Ganga", "Laug", "Dýr"
	);

	@FXML
	private void initialize() {
		people.getItems().addAll(1,2,3,4,5,6); // set 6 til að byrja með
		departure.getItems().addAll(placesList);
		destination.getItems().addAll(placesList);
		interest.getItems().addAll(interestList);
		priceLow.getItems().addAll(10000,20000,30000,40000,50000,60000,70000,80000,90000,100000,110000,120000,130000,140000,150000);
		priceHigh.getItems().addAll(10000,20000,30000,40000,50000,60000,70000,80000,90000,100000,110000,120000,130000,140000,150000);
		System.err.println("test");
	}
	@FXML
	private void submit(ActionEvent event) throws IOException {
		if(name.getText().isEmpty() ||  ssn.getText().isEmpty() ||
			email.getText().isEmpty() || phone.getText().isEmpty() ||  
			people.getValue() == null || interest.getValue() == null ||
			departure.getValue() == null || destination.getValue() == null ||
			departureDate.getValue() == null || returnDate.getValue() == null){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Óútfylltir reitir");
				alert.setHeaderText("Vinsamlegast fylla út alla reiti");
				alert.showAndWait();
		}
		else if (priceLow.getValue().intValue() > priceHigh.getValue().intValue()){
			Alert priceAlert = new Alert(Alert.AlertType.ERROR);
				priceAlert.setTitle("Verð villa");
				priceAlert.setHeaderText("Vinsamlegast veldu lægri mörk verðs sem er lægri en hærri mörkin");
				priceAlert.showAndWait();
		}
		else{
			var user = new User(
					name.getText(),
					email.getText(),
					ssn.getText(),
					phone.getText(),
					people.getValue().intValue(),
					departure.getValue(),
					departureDate.getValue(),
					destination.getValue(),
					departureDate.getValue()
				);

				System.err.println(name.getText());
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

				var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_PackageView.fxml"));
				loader.setControllerFactory(c -> new PackageSelectionController(user));
				var scene = new Scene(loader.load(), 1280, 900);
				stage.setScene(scene);
				stage.show();
		}
	}

	public static List<String> getPlacesList(){
		return placesList;
	}

	public static List<String> getInterestList(){
		return interestList;
	}
}
