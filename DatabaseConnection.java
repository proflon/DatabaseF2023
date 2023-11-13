import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static String username = "adhikarias";
  private static String password = "abc123";

  public static Connection getConnection(String database)
    throws ClassNotFoundException, SQLException {
    Class.forName("com.mysql.jdbc.Driver");
    String databaseLink =
      "jdbc:mysql://cis-lonsmith-student2.ccr8ibhqw8qf.us-east-2.rds.amazonaws.com/" +
      database +
      "?useSSL=false";
    return DriverManager.getConnection(databaseLink, username, password);
  }
}
