package is.hi.travel_planer.view;

import java.io.IOException;
import java.time.LocalDate;

import javafx.util.StringConverter;
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
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;

public class UserController {
	@FXML
	private Button submit;
	@FXML
	private TextField name, ssn, email, phone;
	@FXML
	private ChoiceBox<String> interest, origin, destination;
	@FXML
	private ChoiceBox<Integer> groupSize;
	@FXML
	private ChoiceBox<Optional<Integer>> maxPriceSelect;
	@FXML
	private DatePicker departureDate, returnDate;

	private Optional<Integer> maxPrice = Optional.empty();

	private static List<String> placesList = Arrays.asList(
		"Reykjavík", "Akureyri", "Egilsstaðir", "Húsavík", "Vík", "Keflavík", "Sauðárkrókur", "Stykkishólmur", "Ísafjörður", "Vestmannaeyjar"
	); // man ekki alla staðina, bætum þeim við
	private static List<String> interestList = Arrays.asList(
		"Ekkert áhugamál","Fjölskylduvænt", "Upplifun/Ævintýri", "Bátur", "Safn", "Ganga", "Laug", "Dýr"
	);

	@FXML
	private void initialize() {
		groupSize.getItems().addAll(1,2,3,4,5,6); // set 6 til að byrja með
		groupSize.setValue(1);
		origin.getItems().addAll(placesList);
		destination.getItems().addAll(placesList);
		interest.getItems().addAll(interestList);
		maxPriceSelect.getItems().addAll(
			Optional.empty(),
			Optional.of(10000),
			Optional.of(20000),
			Optional.of(30000),
			Optional.of(40000),
			Optional.of(50000),
			Optional.of(60000),
			Optional.of(70000),
			Optional.of(80000),
			Optional.of(90000),
			Optional.of(100000),
			Optional.of(110000),
			Optional.of(120000),
			Optional.of(130000),
			Optional.of(140000),
			Optional.of(150000)
		);
		maxPriceSelect.setConverter(new StringConverter<Optional<Integer>>() {
			@Override
			public String toString(Optional<Integer> o) {
				if (o == null || o.isEmpty()) {
					return "Ekkert hámark";
				}
				else {
					return Integer.toString(o.get());
				}
			}

			@Override
			public Optional<Integer> fromString(String s) {
				if (s == null || "Ekkert hámark".equals(s))
					return Optional.empty();
				else
					return Optional.of(Integer.parseInt(s));
			}
		});
		interest.setValue("Ekkert áhugamál");
		departureDate.setValue(LocalDate.now());

	}
	@FXML
	private void submit(ActionEvent event) throws IOException {
		if(name.getText().isEmpty() ||  ssn.getText().isEmpty() ||
			email.getText().isEmpty() || phone.getText().isEmpty() ||
			groupSize.getValue() == null ||
			origin.getValue() == null || destination.getValue() == null ||
			departureDate.getValue() == null || returnDate.getValue() == null){
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Óútfylltir reitir");
				alert.setHeaderText("Vinsamlegast fylla út alla nauðsynilega reiti");
				alert.showAndWait();
		}
		if(origin.getValue().equals(destination.getValue())){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Brottför og áfangastaður sá sami");
			alert.setHeaderText("Ekki er hægt að fljúga frá " + origin.getValue() + " til " + destination.getValue());
			alert.showAndWait();
		}
		if(returnDate.getValue().compareTo(departureDate.getValue()) < 0){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Brottför eftir komudag");
			alert.setHeaderText("Ekki er hægt að velja heimkomudagsetningu sem er á undan brottfarardagsetningu");
			alert.showAndWait();
		}
		else{
			int mp;
			if (maxPrice.isPresent()){
				mp = maxPrice.get().intValue();
			}
			else {
				mp = Integer.MAX_VALUE;
			}

			var user = new User(
				name.getText(),
				email.getText(),
				ssn.getText(),
				phone.getText(),
				groupSize.getValue().intValue(),
				origin.getValue(),
				departureDate.getValue(),
				destination.getValue(),
				returnDate.getValue(),
				interest.getValue().equals(interestList.get(0)) ? "" : interest.getValue(),
				mp
			);

			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_PackageView.fxml"));
			loader.setControllerFactory(c -> new PackageSelectionController(user));
			var scene = new Scene(loader.load(), 1280, 900);
			stage.setScene(scene);
		}
	}

	public static List<String> getPlacesList(){
		return placesList;
	}

	public static List<String> getInterestList(){
		return interestList;
	}

	@FXML
	private void handleMaxPriceSelection() {
		maxPrice = maxPriceSelect.getValue();
	}

}
