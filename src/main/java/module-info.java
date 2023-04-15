module is.hi.travel_planer {
	requires java.sql;

	requires transitive javafx.graphics;
	requires javafx.controls;
	requires javafx.fxml;

	opens is.hi.travel_planer.view to javafx.fxml;
	requires is.hi.flight_booking;
	requires com.daytour;

	exports is.hi.travel_planer;
	exports is.hi.travel_planer.mock;
}
