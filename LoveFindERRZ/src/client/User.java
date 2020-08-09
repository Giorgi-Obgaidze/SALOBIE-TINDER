package client;

import database.DataAdministrator;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String username_id;
    private List<String> friendList;
    private DataAdministrator dataAdministrator;
    private String imageFolderPath;
    public User(String username_id, DataAdministrator dataAdministrator) throws SQLException, IOException {
        this.username_id = username_id;
        this.dataAdministrator = dataAdministrator;
        friendList = this.dataAdministrator.getFriends(username_id);
        imageFolderPath = dataAdministrator.getImagePath(username_id);

    }
    //ობგა: ჯიბირაა არა ჯებირა
    //ჯიბირაა: აქ ჩაგიმატე რო ცარიელი თუა ნალი დააბრუნოს თორე მერე სერვლეტის მხარეს პირდაპირ toString-ს ვეღარ შობაა
    public List<String> myNewFriends() throws SQLException {
        List<String> tot = dataAdministrator.getFriends(username_id);
        List<String> res = new ArrayList<>();
        for(String friend : tot){
            if(!friendList.contains(friend)){
                friendList.add(friend);
                res.add(friend);
            }
        }
        if(res.size() == 0) return null;
        return res;
    }

    public List<String> myFriends() throws SQLException {
        List<String> res = dataAdministrator.getFriends(username_id);
        if(res.size() == 0) return null;
        return res;
    }
    //ჯებირაა: მგონი აქაც იგივე პრობლემა მოხდება რაც ზევით იყო რა
    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public String getFriendUsername(String user_id) throws SQLException {
        return dataAdministrator.getData("username", user_id);
    }
    public String getUserId(){
        return username_id;
    }

    public String getDescription() throws SQLException {
        return dataAdministrator.getData("description", username_id);
    }

    public int chooseFriends(String chosen, String status) throws SQLException {
        String curr_status = dataAdministrator.getStatus(chosen, username_id);
        if (status.equals("accept")) {
            if (curr_status == null) {
                dataAdministrator.makeFriend(username_id, chosen, "waiting");
                //dataAdministrator.makeFriend(chosen, username_id, "waiting");
                return 0;
            } else if (curr_status.equals("waiting")) {
                System.out.println("waiting");
                dataAdministrator.makeFriend(username_id, chosen, "match");
                //dataAdministrator.updateStatus(username_id, chosen, "match");
                dataAdministrator.updateStatus(chosen, username_id, "match");
                //ჯებირა: აქ კი შობი იმას რო იმასაც ჩაუწერო რო დამეჩილია მარა იმის ფრენდლისტში
                //რეალურად არაფერიც არ ვარდება მაგიტო დაგიმატებ ამას:
                return 1;
            }
        } else if (status.equals("reject")) {
            dataAdministrator.makeFriend(username_id, chosen, "reject");
            dataAdministrator.makeFriend(chosen, username_id, "reject");
            return -1;
        }
        return -1;
    }
}
