package is.hi.travel_planer.view;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;

import is.hi.travel_planer.model.TravelPackage;
import is.hi.travel_planer.model.User;
import is.hi.travel_planer.model.TourTime;
import is.hi.travel_planer.control.PackageController;
import is.hi.hotel.entities.Room;
import is.hi.flight_booking.application.Seat;


public class PaymentConfirmationController {
	@FXML
	private TextField ccname, ccnr;
	@FXML
	private ChoiceBox<String> month;
	@FXML
	private ChoiceBox<Integer> year;
	@FXML
	private HBox selectedPackage;
    @FXML
    private Button finish, back;
	@FXML
	private Label fxTourLabel;
	@FXML
	private VBox fxSeatLabel, fxRoomLabel;

	private TravelPackage pkg;
	private User user;
	private PackageController packageController;
	private List<Seat> seats;
	private List<Room> rooms;
	private TourTime tourTime;
	private DialogPane dialogPane;

	public PaymentConfirmationController(TravelPackage pkg, User user, PackageController packageController, List<Seat> seats, List<Room> rooms, TourTime tourTime) {
		this.pkg = pkg;
		this.user = user;
		this.packageController = packageController;
		this.seats = seats;
		this.rooms = rooms;
		this.tourTime = tourTime;
	}

	@FXML
	private void initialize() {
		month.getItems().addAll("janúar","febrúar","mars","apríl","maí","júní","júlí","ágúst","september","október","nóvember","desember");
		year.getItems().addAll(2022,2023,2024,2025,2026,2027,2028,2029,2030);

		selectedPackage.getChildren().setAll(new PackageView(pkg));

		for (Seat s : this.seats) {
			Label label = new Label(s.getId());
			fxSeatLabel.getChildren().add(label);
		}

		for (Room r : this.rooms) {
			Label label = new Label(Integer.toString(r.getRoomId()));
			fxRoomLabel.getChildren().add(label);
		}
		fxTourLabel.setText(this.tourTime.toString());

		finish.setDisable(true);

        ccname.textProperty().addListener((observable, oldValue, newValue) -> {
            enableConfirm(ccname, ccnr, month, year, finish);
        });
        
        ccnr.textProperty().addListener((observable, oldValue, newValue) -> {
            enableConfirm(ccname, ccnr, month, year, finish);
        });
        
        month.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableConfirm(ccname, ccnr, month, year, finish);
        });
        
        year.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableConfirm(ccname, ccnr, month, year, finish);
        });
    
    }

    private void enableConfirm(TextField ccname, TextField ccnr, ChoiceBox<String> month, ChoiceBox<Integer> year, Button finish) {
        if (!ccname.getText().isEmpty() && !ccnr.getText().isEmpty() && month.getValue() != null && year.getValue() != null) {
            finish.setDisable(false);
        } else {
            finish.setDisable(true);
        }
    }

	@FXML
	private void handleConfirm() {
		packageController.bookPackage(pkg, this.seats, this.rooms, this.tourTime);

		Stage dialogStage = new Stage();
		dialogStage.setTitle("Greiðsla móttekin");

		VBox dialogVbox = new VBox(20);
		dialogVbox.setAlignment(Pos.CENTER);

		Label messageLabel = new Label("Bókun lokið. Staðfesting hefur verið send með tölvupósti.");
		Button closeButton = new Button("Loka");
		Button backButton = new Button("Bóka aðra ferð");

		HBox buttonBox = new HBox(20);
		buttonBox.setAlignment(Pos.CENTER);
		buttonBox.getChildren().addAll(closeButton, backButton);

		dialogVbox.getChildren().addAll(messageLabel, buttonBox);

		closeButton.setOnAction(event -> {
			dialogStage.close();
			Platform.exit();
		});

		backButton.setOnAction(event -> {
			dialogStage.close();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_InitialView.fxml"));
			try {
				Scene scene = new Scene(loader.load(), 1280, 900);
				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(scene);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Scene dialogScene = new Scene(dialogVbox, 400, 150);
		dialogStage.setScene(dialogScene);
		dialogStage.showAndWait();
	}



	@FXML
	private void handleBack(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

		var loader = new FXMLLoader(getClass().getResource("/fxml/DetailSelection.fxml"));
		loader.setControllerFactory(c -> new DetailSelectionController(pkg, packageController));
		var scene = new Scene(loader.load(), 1280, 900);
		stage.setScene(scene);
	}
}
