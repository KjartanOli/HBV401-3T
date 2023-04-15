package is.hi.hotel.implementations.db;

import java.sql.*;

public class DatabaseConnection {
    private final Connection connection;
    private final Statement statement;

    public DatabaseConnection(String connectionUrl) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        this.connection = DriverManager.getConnection(connectionUrl);
        this.statement = connection.createStatement();
    }

    public ResultSet executeSQL(String sqlString ) throws SQLException {
        return statement.executeQuery(sqlString);
    }
}
