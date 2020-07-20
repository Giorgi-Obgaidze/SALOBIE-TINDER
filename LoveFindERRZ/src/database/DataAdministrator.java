package database;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAdministrator {
    public static final String AttributeName = "DAdministrator";
    private static List<String> IDList;
    private Connection connection;

    public DataAdministrator() throws SQLException, ClassNotFoundException {
        DBConnection connect = new DBConnection();
        connection = connect.getConnection();
        createIdList();
    }

    private void createIdList() throws SQLException {
        IDList = new ArrayList<>();
        String command = "SELECT user_id FROM users;";
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(command);
        while(rs.next()){
            IDList.add(rs.getString(1));
        }
    }

    public List<String> getIDList(){
        return IDList;
    }

    public String getData(String column_name, String acc_id) throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(String.format("select * from users where '%s' like '%s';", column_name, acc_id));
        if(rs.next()){
            return rs.getString(column_name);
        }
        return null;
    }

    public void addNewAccount(String username, String password, String id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into users(user_id, username, password) " + "values(?, ?, ?)");
        ps.setString(1, id);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.executeUpdate();
    }

    public void addCard(String id,String description) throws SQLException {
        String command = "Update users set description=? where user_id=" + id;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, description);
        ps.executeUpdate();
    }

    public void addImage(String new_image){

    }

}
