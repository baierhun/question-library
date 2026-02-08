package backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable {
    private Connection connection;
    private String URL;

    public Database(String URL) {
        this.URL = URL;
    }

    /*
    Connects to the database
     */
    public void connect() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
    }

    /*

     */
    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException s) {
                System.err.println("There was an error closing the db connection.");
            }
        }
    }
}
