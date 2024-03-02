package dataAccess;


import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class AccessUserData {
    public Map<String, UserData> allUserData;

    public AccessUserData() {
        this.allUserData = new HashMap<>();
    }
    public void clear() {
        this.allUserData.clear();
    }
    public void createUser(String username, String password, String email){
        allUserData.put(username, new UserData(username, password, email));
    }
    public UserData getUser(String username){
        return allUserData.get(username);
    }
}
