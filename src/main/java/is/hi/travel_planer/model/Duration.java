package is.hi.travel_planer.model;

import java.time.LocalDate;

public class Duration {
	private final LocalDate start;
	private final LocalDate end;

	public Duration(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}

	public LocalDate getStart() { return this.start; }
	public LocalDate getEnd() { return this.end; }
}
