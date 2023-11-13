import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static String database =
    "jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/COMPANY?useSSL=false";
  private static String username = "adhikarias";
  private static String password = "abc123";

  public static Connection getConnection()
    throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    return DriverManager.getConnection(database, username, password);
  }
}
