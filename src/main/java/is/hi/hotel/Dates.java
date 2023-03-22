package is.hi.hotel;

import java.time.LocalDate;

public class Dates {
    private LocalDate dateIn;
    private LocalDate dateOut;

    public Dates(LocalDate dateIn, LocalDate dateOut){
        this.dateIn = dateIn;
        this.dateOut = dateOut;
    }

    public LocalDate getDateIn() { return this.dateIn; }
	public LocalDate getDateOut() { return this.dateOut; }
}

