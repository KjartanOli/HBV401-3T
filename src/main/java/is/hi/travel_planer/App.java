package is.hi.travel_planer;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import is.hi.travel_planer.view.UserController;

/**
 * JavaFX App
 */
public class App extends Application {
	@Override
	public void start(Stage stage) throws IOException {
		var loader = new FXMLLoader(getClass().getResource("/fxml/TravelPlanner_InitialView.fxml"));

		var scene = new Scene(loader.load(), 800, 800);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}
}
