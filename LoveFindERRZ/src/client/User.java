package client;

import database.DataAdministrator;

import java.sql.SQLException;
import java.util.List;

public class User {
    private String username_id;
    private List<String> friendList;
    private DataAdministrator dataAdministrator;
    private String imageFolderPath;
    public User(String username_id, DataAdministrator dataAdministrator) throws SQLException {
        this.username_id = username_id;
        this.dataAdministrator = dataAdministrator;
        friendList = this.dataAdministrator.getFriends(username_id);
        imageFolderPath = dataAdministrator.getImagePath(username_id);
    }

    public List<String> myFriends(){
        return friendList;
    }

    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public String getUserId(){
        return username_id;
    }

    public void makeFriends(String chosen, String status) throws SQLException {
        String curr_status = dataAdministrator.getStatus(chosen, username_id);
        if (status.equals("accept")) {
            if (curr_status.equals("waiting")) {
                dataAdministrator.updateStatus(username_id, chosen, "match");
                dataAdministrator.updateStatus(chosen, username_id, "match");
            } else if (curr_status.equals(null)) {
                dataAdministrator.makeFriend(username_id, chosen, "waiting");
            }
        } else if (status.equals("reject")) {
            dataAdministrator.makeFriend(username_id, chosen, "reject");
        }

    }
}
