package is.hi.hotel.implementations.db;

import is.hi.hotel.entities.Hotel;
import is.hi.hotel.entities.Room;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;

public class Connect {
    private static int cnt = 0;
    private static final String myRelativePath = "/Users/lenamaria/Documents/Hi23/Þróun hugbúnaðar/Iceleep/db/hotel.db";

    public static Connection getConnection() {
        String connectionUrl = "jdbc:sqlite:" + myRelativePath;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(connectionUrl);
            stmt = con.createStatement();
            String SQL = "SELECT * FROM Hotel";
            rs = stmt.executeQuery(SQL);

            ArrayList<Hotel> hotels = new ArrayList<>();

            // Get hotels first and add to hotel list, with no rooms for the time being.
            while (rs.next()) {
                Hotel hotel = new Hotel(rs.getInt("hotelId"), rs.getString("name"), rs.getString("location"), null);
                hotels.add(hotel);
                System.out.println("Hotel name: " + hotel.getName());
            }
            // Populate hotels with their rooms.
            for(Hotel hotel : hotels) {
                rs = stmt.executeQuery("SELECT * FROM Rooms WHERE Rooms.hotelId = " + hotel.getHotelId());
                while (rs.next()) {
                    Room room = new Room(rs.getInt("roomId"), rs.getInt("capacity"), null, rs.getInt("price"));
                }
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        // CLose connections
        finally {
            try { rs.close(); } catch (Exception e) { /* Ignored */ }
            try { stmt.close(); } catch (Exception e) { /* Ignored */ }
            try { con.close(); } catch (Exception e) { /* Ignored */ }
        }
        return null;
    }

    /**
     * Sækir allar dagsferðirnar og skilar heildarlistanum.
     *  Tenging er aðeins mynduð einu sinni svo heildarlistinn haldi upprunalegri stærð.
     */

    /*
    public static ArrayList<DaytourType> getDaytours() {
        cnt++;
        if (cnt == 1) {
            getConnection();
            return heildarlisti;
        }
        else{
            return heildarlisti;
        }
    }
    */
}
