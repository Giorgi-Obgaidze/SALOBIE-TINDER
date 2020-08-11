package signUp;

import database.DataAdministrator;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class AccountManagerTest {
    DataAdministrator da;
    AccountManager am;

    @Before
    public void start() throws SQLException, ClassNotFoundException {
        da = new DataAdministrator();
        am = new AccountManager(da);
        da.addNewAccount("goderdza", "9d61ba84065fc83956cdfc63e49bc7a9d21d8665", "10311110010111410012297", "IMAGES/images3333");
        da.addNewAccount("xvisto", "297f597f53fdf2fef50e54937f679be25da9aaf0", "120118105115116111", "IMAGES/image2222");
        da.addNewAccount("mercxali", "63f879d7ef9918f41c2e08b9654928e17c58a5c5", "1091011149912097108105", "IMAGES/images1111");
        da.addNewAccount("guja", "962feaa51534bece684f82a18c4b7ae00aefe94c", "10311710697", "IMAGES/images4444");
        da.addNewAccount("begura", "ad8841f91794c8edec694a8a713643bbd1113b98", "9810110311711497", "IMAGES/images5555");
        da.addNewAccount("gorila", "fb94c58b7a70c3e0a02a1b71cd2e75a1ad36d948", "10311111410510897", "IMAGES/images6666");
    }

    @Test
    public void accountExists() throws SQLException {
        assertTrue(am.accountExists("goderdza"));
        assertTrue(am.accountExists("xvisto"));
        assertTrue(am.accountExists("mercxali"));
        assertTrue(am.accountExists("guja"));
        assertTrue(am.accountExists("begura"));
        assertTrue(am.accountExists("gorila"));
        assertTrue(!am.accountExists("maka"));
        assertTrue(!am.accountExists("dito"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void accessGranted() throws SQLException {
        assertTrue(am.accessGranted("goderdza", "232323"));
        assertTrue(am.accessGranted("xvisto", "212325"));
        assertTrue(!am.accessGranted("mercxali", "48"));
        assertTrue(!am.accessGranted("begura", "874543546"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void createAccount() throws SQLException {
        am.createAccount("merabi", "243243", "IMAGES/images");
        assertTrue(am.accountExists("merabi"));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }

    @Test
    public void getID() throws SQLException {
        am.createAccount("merabi", "243243", "IMAGES/images");
        assertTrue("1091011149798105".equals(am.getID()));
        da.dropTable("users");
        da.dropTable("matchingTable");
    }
}