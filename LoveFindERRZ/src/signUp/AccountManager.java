package signUp;

import database.DBConnection;
import database.DataAdministrator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountManager {
    private Connection connect;
    private DBConnection db;
    private DataAdministrator DAdministrator;
    private String user_id;
    public static final String AttributeName = "account manager";

    public AccountManager(DataAdministrator administrator)  {
        DAdministrator = administrator;
    }

    public boolean accountExists(String username){
        return false;
    }

    public boolean accessGranted(String username, String password){
        return false;
    }

    public void createAccount(String username, String passwords){

    }

    public String getID(){
        return user_id;
    }

    private void createID(String username){
        String id = "";
        for(int i = 0; i < username.length(); i++){
            int num_value = username.charAt(i);
            id += num_value;
        }
        user_id = id;
    }
}
