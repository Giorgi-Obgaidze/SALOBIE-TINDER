package database;

import javax.print.DocFlavor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        ResultSet rs = stm.executeQuery(String.format("select * from users where user_id like '%s';", acc_id));
        if(rs.next()){
            return rs.getString(column_name);
        }
        return null;
    }

    public void addNewAccount(String username, String password, String id, String folderPath) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into users(user_id, username, password, imageCount, imageFolder) " + "values(?, ?, ?, ?, ?)");
        ps.setString(1, id);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setInt(4, 0);
        ps.setString(5, folderPath);

        ps.executeUpdate();
    }

    public void addCard(String id,String description) throws SQLException {
        String command = "Update users set description=? where user_id=" + id;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, description);
        ps.executeUpdate();
    }

    public void addImage(String id, int count,String new_image) throws FileNotFoundException, SQLException {
        File image = new File(new_image);
        FileInputStream fis = new FileInputStream(image);
        String command = "Update users set image" + count + "=? where user_id=" + id;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setBinaryStream(1, (InputStream)fis, (int)(image.length()));
        ps.executeUpdate();
    }

    public void updateStatus(String chooser, String chosen, String status) throws SQLException {
        String command = "UPDATE matchingTable set status=? where chooser=" + chooser + " And " + "chosen = " + chosen;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, status);
        ps.executeUpdate();
    }


    public void makeFriend(String chooser, String chosen, String status) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into matchingTable(chooser, chosen, status)" + "values (?, ?, ?)");
        ps.setString(1, chooser);
        ps.setString(2, chosen);
        ps.setString(3, status);
        ps.executeUpdate();
    }

    public String getImagePath(String user_id) throws SQLException {
        String command = "Select imageFolder From users where user_id = " + user_id;
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(command);
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public String getStatus(String chooser, String chosen) throws SQLException {
        String command = "SELECT status FROM matchingTable Where chooser = " + chooser + " AND " + "chosen = " + chosen;
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(command);
        if(rs.next()){
            return rs.getString(1);
        }
        return null;

    }

    public Connection getBaseConnection(){
        return connection;
    }

    public List<String> getFriends(String user_id) throws SQLException {
        List<String> myFriends = new ArrayList<>();
        String command = "SELECT chosen FROM matchingTable WHERE status = \"match\" AND chooser = " + user_id;
        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(command);
        while (rs.next()){
            myFriends.add(rs.getString(1));
        }
        return  myFriends;
    }

}
