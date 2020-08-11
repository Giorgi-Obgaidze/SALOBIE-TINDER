package client;

import database.DataAdministrator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserTest {
    private User user;
    DataAdministrator da;

    @Before
    public void start() throws SQLException, ClassNotFoundException, IOException {
        da = new DataAdministrator();
        da.addNewAccount("goderdza", "232323", "3333", "IMAGES/images3333");
        da.addNewAccount("xvisto", "212325", "2222", "IMAGES/image2222");
        da.addNewAccount("mercxali", "202227", "1111", "IMAGES/images1111");
        da.addNewAccount("guja", "131343", "4444", "IMAGES/images4444");
        da.addNewAccount("begura", "060090", "5555", "IMAGES/images5555");
        da.addNewAccount("gorila", "630204", "6666", "IMAGES/images6666");

        da.makeFriend("3333", "2222", "match");
        da.makeFriend("3333", "1111", "match");
        da.makeFriend("3333", "4444", "waiting");
        da.makeFriend("2222", "3333","match");
        da.makeFriend("1111", "3333", "match");
        da.makeFriend("3333", "5555", "waiting");
        da.makeFriend("3333", "6666", "reject");
        da.makeFriend("6666", "3333", "reject");

        user = new User("3333", da);

    }

    @Test
    public void myNewFriends() throws SQLException {
        da.makeFriend("3333", "4444", "match");
        da.makeFriend("4444", "3333", "match");
        List<String> newFriends = user.myNewFriends();
        assertEquals(1,newFriends.size());
        assertTrue(newFriends.contains("4444"));

        List<String> newNewFriends = user.myNewFriends();
        assertEquals(null, newNewFriends);
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void myFriends() throws SQLException, IOException {
        List<String> myFriends = user.myFriends();
        assertEquals(2, myFriends.size());
        assertTrue(myFriends.contains("1111") && myFriends.contains("2222"));

        User otherUser = new User("6666", da);
        assertEquals(null, otherUser.myFriends());
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getImageFolderPath() throws SQLException, IOException {
        User user1 = new User("6666", da);
        User user2 = new User("1111", da);
        User user3 = new User("2222", da);
        User user4 = new User("4444", da);
        User user5 = new User("5555", da);

        assertTrue("IMAGES/images3333".equals(user.getImageFolderPath()));
        assertTrue("IMAGES/image2222".equals(user3.getImageFolderPath()));
        assertTrue("IMAGES/images1111".equals(user2.getImageFolderPath()));
        assertTrue("IMAGES/images4444".equals(user4.getImageFolderPath()));
        assertTrue("IMAGES/images5555".equals(user5.getImageFolderPath()));
        assertTrue("IMAGES/images6666".equals(user1.getImageFolderPath()));

        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getFriendUsername() throws SQLException {
        assertTrue("xvisto".equals(user.getFriendUsername("2222")));
        assertTrue("mercxali".equals(user.getFriendUsername("1111")));
        assertEquals(null, user.getFriendUsername("9999"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getUserId() throws SQLException {
        assertTrue("3333".equals(user.getUserId()));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getDescription() throws SQLException {
        da.addCard("3333", "orjer ori");
        assertTrue("orjer ori".equals(user.getDescription()));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void chooseFriends() throws IOException, SQLException {
        da.addNewAccount("1punchMan", "210912", "7777", "IMAGES/images7777");
        da.addNewAccount("1HighVolume", "1453", "8888", "IMAGES/image8888");
        User user1 = new User("4444", da);
        user1.chooseFriends("3333", "accept");
        user.chooseFriends("7777", "accept");
        user.chooseFriends("8888", "reject");
        assertEquals(-1, user.chooseFriends("9999", "bla"));

        assertTrue("match".equals(da.getStatus("3333", "4444")));
        assertTrue("match".equals(da.getStatus("4444", "3333")));

        assertTrue("waiting".equals(da.getStatus("3333", "7777")));
        assertTrue("reject".equals(da.getStatus("3333", "8888")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }
}