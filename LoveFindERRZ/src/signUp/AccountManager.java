package signUp;

import database.DataAdministrator;

import java.sql.SQLException;

public class AccountManager {
    private DataAdministrator DAdministrator;
    private String user_id;
    public static final String AttributeName = "account manager";

    public AccountManager(DataAdministrator administrator)  {
        DAdministrator = administrator;
    }

    public boolean accountExists(String username) throws SQLException {
        createID(username);
        if(DAdministrator.getData("username", user_id) != null)return true;
        return false;
    }

    public boolean accessGranted(String username, String password) throws SQLException {
        createID(username);
        String hashed_password = PasswordEncryption.generatingTheHash(password);
        if(DAdministrator.getData("password", user_id).equals(hashed_password)) return true;
        return false;
    }

    public void createAccount(String username, String password, String folderPath) throws SQLException {
        createID(username);
        String hashed_password = PasswordEncryption.generatingTheHash(password);
        DAdministrator.addNewAccount(username, hashed_password, user_id, folderPath + user_id);
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
