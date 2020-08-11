package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAdministrator {
    public static final String AttributeName = "DAdministrator";
    private Connection connection;

    public DataAdministrator() throws SQLException, ClassNotFoundException {
        DBConnection connect = new DBConnection();
        connection = connect.getConnection();
    }

    public void dropTable(String table) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("truncate " + table + ";");
        ps.executeUpdate();
    }


    public synchronized String getData(String column_name, String acc_id) throws SQLException {
        Statement stm = connection.createStatement();
        ResultSet rs = stm.executeQuery(String.format("select * from users where user_id like '%s';", acc_id));
        if(rs.next()){
            return rs.getString(column_name);
        }
        return null;
    }

    public synchronized void addNewAccount(String username, String password, String id, String folderPath) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into users(user_id, username, password, imageCount, imageFolder) " + "values(?, ?, ?, ?, ?)");
        ps.setString(1, id);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setInt(4, 0);
        ps.setString(5, folderPath);

        ps.executeUpdate();
    }

    public synchronized void addCard(String id,String description) throws SQLException {
        String command = "Update users set description=? where user_id=" + id;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, description);
        ps.executeUpdate();
    }


    public synchronized void updateStatus(String chooser, String chosen, String status) throws SQLException {
        String command = "UPDATE matchingTable set status=? where chooser=" + chooser + " And " + "chosen = " + chosen;
        PreparedStatement ps = connection.prepareStatement(command);
        ps.setString(1, status);
        ps.executeUpdate();
    }


    public synchronized void makeFriend(String chooser, String chosen, String status) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("insert into matchingTable(chooser, chosen, status)" + "values (?, ?, ?)");
        ps.setString(1, chooser);
        ps.setString(2, chosen);
        ps.setString(3, status);
        ps.executeUpdate();
    }

    public synchronized String getImagePath(String user_id) throws SQLException {
        String command = "Select imageFolder From users where user_id = " + user_id;
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(command);
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

    public synchronized String getStatus(String chooser, String chosen) throws SQLException {
        String command = "SELECT status FROM matchingTable Where chooser = " + chooser + " AND " + "chosen = " + chosen;
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(command);
        if(rs.next()){
            return rs.getString(1);
        }
        return null;

    }

    public synchronized List<String> getFriends(String user_id) throws SQLException {
        List<String> myFriends = new ArrayList<>();
        String command = "SELECT chosen FROM matchingTable WHERE (status = \"match\" or status = \"nextstep\") AND chooser =" + user_id;
        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(command);
        while (rs.next()){
            String frMem = rs.getString(1);
            myFriends.add(frMem);
        }
        return  myFriends;
    }

    public synchronized String getNextMatch(String user_id) throws SQLException {
        String command = " select user_id from users where user_id not in (select chosen from users u join matchingTable" +
                " n on u.user_id = n.chooser where user_id = " + user_id + ") and user_id !=" + user_id + " order by RAND() LIMIT 1";
        Statement s = connection.createStatement();
        ResultSet rs = s.executeQuery(command);
        if(rs.next()){
            return rs.getString(1);
        }
        return null;
    }

}
