package is.hi.travel_planer.view;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

import is.hi.flight_booking.application.Flight;
import is.hi.flight_booking.application.Seat;

public class SeatSelector extends GridPane {
	private static class SeatView extends Label {
		private static Background reserved = new Background(
			new BackgroundFill(Color.rgb(255, 0, 0, 1), new CornerRadii(0), new Insets(0.5)));
		private static Background selected = new Background(
			new BackgroundFill(Color.rgb(0, 0, 255, 1), new CornerRadii(0), new Insets(0.5)));
		private static Background free = new Background(
			new BackgroundFill(Color.rgb(0, 255, 0, 1), new CornerRadii(0), new Insets(0.5)));

		private static enum State {
			RESERVED,
			FREE,
			SELECTED;
		}

		private State state;
		private final Seat seat;

		public SeatView(Seat seat) {
			this.seat = seat;
			this.setPrefWidth(30);
			this.setPrefHeight(30);
			this.setAlignment(Pos.CENTER);
			this.setText(seat.getId());
			this.state = seat.isReserved() ? State.RESERVED : State.FREE;
			this.setBackground();

			this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
					switch (this.state) {
						case RESERVED:
							e.consume();
							return;
						case FREE:
							SeatSelector selector = (SeatSelector) this.getParent();
							if (selector.getSelected().size() == selector.getGroupSize())
								break;
							this.state = State.SELECTED;
							selector.select(this.seat);
							break;
						case SELECTED:
							this.state = State.FREE;
							((SeatSelector) this.getParent()).deselect(this.seat);
							break;
					}
					this.setBackground();
					e.consume();
				});
		}

		private void setBackground() {
			Background background;
			switch (this.state) {
				case RESERVED:
					background = reserved;
					break;
				case FREE:
					background = free;
					break;
				case SELECTED:
					background = selected;
					break;
				default:
					// This will never be reached, but the compiler does
					// not verify that we have covered all possible values
					// of the State enum.
					background = null;
					break;
			}
			this.setBackground(background);
		}
	}

	private Set<Seat> selected;
	private int groupSize = 1;

	public SeatSelector() {
		super();
		this.setHgap(10);
		this.setVgap(10);
		this.selected = new HashSet<Seat>();
	}

	public SeatSelector(Flight flight) {
		this();
		this.setFlight(flight);
	}

	public SeatSelector(Flight flight, int groupSize) {
		this(flight);
		this.setGroupSize(groupSize);
	}

	public void setGroupSize(int size) {
		this.groupSize = size;
	}

	public int getGroupSize() {
		return this.groupSize;
	}

	public void setFlight(Flight flight) {
		SortedMap<String, List<Seat>> columns = new TreeMap<String, List<Seat>>();

		for (var seat : flight.getSeats()) {
			String id = seat.getId();

			String[] tmp = id.split("-");

			var column = tmp[0];

			if (!columns.containsKey(column))
				columns.put(column, new ArrayList<Seat>());
			columns.get(column).add(seat);
		}

		int column = 0;
		for (var seats : columns.values()) {
			for (var seat : seats) {
				var view = new SeatView(seat);
				int row = Integer.parseInt(seat.getId().split("-")[1]);
				this.add(view, column, row);
			}
			++column;
		}
	}

	public void select(Seat seat) {
		this.selected.add(seat);
	}

	public void deselect(Seat seat) {
		this.selected.remove(seat);
	}

	public Set<Seat> getSelected() {
		return this.selected;
	}
}
