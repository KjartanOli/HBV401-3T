package is.hi.hotel.implementations.db;

import java.sql.*;

public class DatabaseConnection {
    private Connection connection;
    private Statement statement;

    public DatabaseConnection(String connectionUrl) throws SQLException {
        this.connection = DriverManager.getConnection(connectionUrl);
        this.statement = connection.createStatement();
    }

    public ResultSet executeSQL(String sqlString ) throws SQLException {
        return statement.executeQuery(sqlString);
    }
}
