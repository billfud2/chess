package dataAccess;


import model.UserData;
import java.util.HashMap;
import java.util.Map;

public class AccessUserData {
    Map<String, UserData> allUserData;

    public AccessUserData() {
        this.allUserData = new HashMap<>();
    }
    private void clear() {
        this.allUserData.clear();
    }
    private void createUser(String username, String password, String email){
        allUserData.put(username, new UserData(username, password, email));
    }
    private UserData getUser(String username){
        return allUserData.get(username);
    }
}
