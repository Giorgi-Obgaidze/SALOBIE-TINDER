package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
    private Connection data_connection;

    public DBConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        data_connection = DriverManager.getConnection("jdbc:mysql://" + "localhost?useSSL=false&allowPublicKeyRetrieval=true", "root", "1234");
        Statement statement = data_connection.createStatement();
        statement.executeQuery("use user_base;");
    }

    public Connection getConnection(){
        return data_connection;
    }
}
