import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void run() throws SQLException {
        String url = "jdbc:sqlite:db";
        try (
                Database db = new Database(url);
        ) {
            db.connect();
            Connection conn = db.getConnection();
            System.out.println("connected!");
        }
    }
}
