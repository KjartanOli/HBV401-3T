module is.hi.travel_planer {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires is.hi.flight_booking;
    requires com.daytour;
    exports is.hi.travel_planer;
    exports is.hi.travel_planer.mock;
}
