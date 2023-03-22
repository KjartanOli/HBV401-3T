package is.hi.hotel;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class HotelRepository {
    public List<Hotel> search(Dates dates, int adults, int children, String location){
        Hotel H1 = new Hotel(1, "Akureyri", 1, new Room(101, 4, false, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

        Hotel H2 = new Hotel(2, "Egilsstaðir", 1, new Room(101, 4, false, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

        Hotel H3 = new Hotel(3, "Vík", 1, new Room(101, 4, false, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

        Hotel H4 = new Hotel(4, "Akureyri", 1, new Room(101, 2, true, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

        Hotel H5 = new Hotel(5, "Ísafjörður", 1, new Room(101, 6, true, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));

        Hotel H6 = new Hotel(6, "Siglufjörður", 1, new Room(101, 2, false, 
            new Dates(LocalDate.of(2023,3,24), LocalDate.of(2023,3,27))));
        
        List<Hotel> hotelList = new ArrayList<Hotel>();

        hotelList.add(H1);
        hotelList.add(H2);
        hotelList.add(H3);
        hotelList.add(H4);
        hotelList.add(H5);
        hotelList.add(H6);
        
        return hotelList;

    }
}
