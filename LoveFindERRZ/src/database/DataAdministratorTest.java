package database;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataAdministratorTest {
    private DataAdministrator da;

    @Before
    public void begin() throws SQLException, ClassNotFoundException {
        da = new DataAdministrator();
        da.addNewAccount("datka15", "3b28a8fcc0de354c8cb87b1bf655fdb598b93941", "10097116107974953", "IMAGES/images10097116107974953");
        da.addNewAccount("shoka_13", "23b36ea4f70670ae377a591fdc03d36a9bebb481", "11510411110797954951", "IMAGES/image11510411110797954951");
        da.addNewAccount("jondi19", "6f07a6148c052cba5539ee651379ed156aea5701", "1061111101001054957", "IMAGES/images1061111101001054957");
        da.addCard("10097116107974953", "I'm cool");
        da.addCard("11510411110797954951", "I'm hot");

        da.makeFriend("10097116107974953", "11510411110797954951", "match");
        da.makeFriend("11510411110797954951", "10097116107974953", "match");
        da.makeFriend("1061111101001054957", "11510411110797954951", "waiting");
        da.makeFriend("10097116107974953", "1061111101001054957", "reject");


    }


    @Test
    public void getData() throws SQLException {
        assertEquals("datka15", da.getData("username", "10097116107974953"));
        assertEquals("3b28a8fcc0de354c8cb87b1bf655fdb598b93941", da.getData("password", "10097116107974953"));
        assertEquals("I'm cool", da.getData("description", "10097116107974953"));
        assertEquals("IMAGES/images10097116107974953", da.getData("imageFolder", "10097116107974953"));

        assertEquals("shoka_13", da.getData("username", "11510411110797954951"));
        assertEquals("23b36ea4f70670ae377a591fdc03d36a9bebb481", da.getData("password", "11510411110797954951"));
        assertEquals("I'm hot", da.getData("description", "11510411110797954951"));
        assertEquals("IMAGES/image11510411110797954951", da.getData("imageFolder", "11510411110797954951"));

        assertEquals(null, da.getData("bla", "107974953"));
        assertEquals(null, da.getData("username", "1007116107974953"));
        assertEquals(null, da.getData("password", "10096107974953"));
        assertEquals(null, da.getData("description", "197116107974953"));
        assertEquals(null, da.getData("imageFolder", "1006107974953"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void addNewAccount() throws SQLException {
        assertTrue("jondi19".equals(da.getData("username", "1061111101001054957")));
        assertTrue("6f07a6148c052cba5539ee651379ed156aea5701".equals(da.getData("password", "1061111101001054957")));
        assertTrue("1061111101001054957".equals(da.getData("user_id", "1061111101001054957")));
        assertTrue("IMAGES/images1061111101001054957".equals(da.getData("imageFolder", "1061111101001054957")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void addCard() throws SQLException {
        da.addCard("1061111101001054957", "I'm desperate");
        assertTrue("I'm desperate".equals(da.getData("description", "1061111101001054957")));
        da.addCard("1061111101001054957", "I'm awesome");
        assertTrue("I'm awesome".equals(da.getData("description", "1061111101001054957")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void updateStatus() throws SQLException {
        da.updateStatus("1061111101001054957", "11510411110797954951", "reject");
        assertTrue("reject".equals(da.getStatus("1061111101001054957", "11510411110797954951")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void makeFriend() throws SQLException {
        da.makeFriend("1313", "4242", "waiting");
        da.makeFriend("6666", "1342", "reject");
        da.makeFriend("2346", "3336", "match");

        assertTrue("waiting".equals(da.getStatus("1313", "4242")));
        assertTrue("reject".equals(da.getStatus("6666", "1342")));
        assertTrue("match".equals(da.getStatus("2346", "3336")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getImagePath() throws SQLException {
        assertTrue("IMAGES/images10097116107974953".equals(da.getImagePath("10097116107974953")));
        assertEquals(null, (da.getImagePath("100916107974953")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getStatus() throws SQLException {
        assertTrue("match".equals(da.getStatus("10097116107974953", "11510411110797954951")));
        assertTrue("match".equals(da.getStatus("11510411110797954951", "10097116107974953")));
        assertTrue("waiting".equals(da.getStatus("1061111101001054957", "11510411110797954951")));
        assertTrue("reject".equals(da.getStatus("10097116107974953", "1061111101001054957")));

        assertEquals(false, equals(da.getStatus("1009711674953", "11510411110797954951")));
        assertEquals(false, equals(da.getStatus("11510411110797954951", "1009107974953")));
        assertEquals(false, equals(da.getStatus("11510411110797954951", "1061111101001054957")));
        assertEquals(false, equals(da.getStatus("1009711610953", "10611111054957")));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }


    @Test
    public void getFriends() throws SQLException {
        List<String> friends = da.getFriends("10097116107974953");
        assertTrue(friends.contains("11510411110797954951"));
        assertTrue(!friends.contains("2131232134322131"));
        assertTrue(!friends.contains("1061111101001054957"));

        List<String> friends2 = da.getFriends("1061111101001054957");
        assertTrue(friends2.size() == 0);

        List<String> friends3 = da.getFriends("1111101001054957");
        assertTrue(friends3.size() == 0);
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getNextMatch() throws SQLException {
        String next = da.getNextMatch("10097116107974953");
        assertTrue(next == null);
        da.addNewAccount("gocha", "3b28a8fcc0de354c8cb87b1bf655fdb598b93941", "1009711610797495", "IMAGES/images10097116107974953");
        da.addNewAccount("xvicha", "23b36ea4f70670ae377a591fdc03d36a9bebb481", "1151041111079795495", "IMAGES/image11510411110797954951");
        da.addNewAccount("nona", "6f07a6148c052cba5539ee651379ed156aea5701", "106111110100105495", "IMAGES/images1061111101001054957");

        next = da.getNextMatch("10097116107974953");
        assertTrue(next.equals("1009711610797495") || next.equals("1151041111079795495") || next.equals("106111110100105495"));
        next = da.getNextMatch("10097116107974953");
        assertTrue(next.equals("1009711610797495") || next.equals("1151041111079795495") || next.equals("106111110100105495"));
        next = da.getNextMatch("10097116107974953");
        assertTrue(next.equals("1009711610797495") || next.equals("1151041111079795495") || next.equals("106111110100105495"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }
}