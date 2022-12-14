import java.sql.*;
import java.text.SimpleDateFormat;


public class DBConnection  {

    private static Connection connection;

    private final static String dbName = "learn";
    private final static String dbUser = "root";
    private final static String dbPass = "password";
    private static StringBuilder insertQuery = new StringBuilder();
    private static final SimpleDateFormat birthDayFormat = new SimpleDateFormat("yyyy.MM.dd");

    public static Connection getConnection() {

        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/" + dbName;
                connection = DriverManager.getConnection(url, dbUser, dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                    //"id INT NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(200), " +
                    "birthDate DATE NOT NULL, " +
                    "count INT NOT NULL, " +
                        "PRIMARY KEY (name))");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void executeMultiInsert() throws SQLException{
        String sql = "INSERT INTO voter_count(name, birthDate, count)" +
                " VALUES " + insertQuery.toString() +
                " ON DUPLICATE KEY UPDATE count = count + 1";
        DBConnection.getConnection().createStatement().execute(sql);
        insertQuery = new StringBuilder();
    }

    public static void countVoter(Voter voter)  throws  SQLException{
        String birthDay = birthDayFormat.format(voter.getBirthDay());
        String name = voter.getName();
        insertQuery.append(insertQuery.isEmpty() ? "" : ",").append("('").append(name).append("', '").append(birthDay).append("', 1)");
        if (insertQuery.length() > 50_500_000) {
                executeMultiInsert();
            }
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" + rs.getString("name") + " (" +
                rs.getString("birthDate") + ") - " + rs.getInt("count"));
        }
    }
}
