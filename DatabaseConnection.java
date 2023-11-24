import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection getConnection(String inputDatabase) throws ClassNotFoundException, SQLException {
        try {
            FileReader reader = new FileReader("database.props");
            Properties properties = new Properties();
            properties.load(reader);

            String dbUrlTemplate = properties.getProperty("db.url");
            String dbUser = properties.getProperty("db.user");
            String dbPassword = properties.getProperty("db.password");
            String dbDriver = properties.getProperty("db.driver");

            // Validate the entered database name
            String dbUrl = String.format(dbUrlTemplate, inputDatabase);

            if (!isValidDatabase(dbUrl, dbUser, dbPassword, dbDriver)) {
                throw new RuntimeException("Invalid database name: " + inputDatabase);
            }

            Class.forName(dbDriver);
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read database properties file.");
        }
    }

    private static boolean isValidDatabase(String dbUrl, String dbUser, String dbPassword, String dbDriver) {
        try {
            // Try to establish a connection to validate the database parameters
            Connection testConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            testConnection.close(); // Close the test connection
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}