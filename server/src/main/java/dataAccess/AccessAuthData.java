package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;

public class AccessAuthData {
    Map<String, AuthData> allAuthData;

    public AccessAuthData() {
        this.allAuthData = new HashMap<>();
    }
    public void clear() {
        this.allAuthData.clear();
    }
    private void createAuth(String username, String authToken){
        allAuthData.put(authToken, new AuthData(username, authToken));
    }
    private AuthData getAuth(String authToken){
        return allAuthData.get(authToken);
    }
    private void deleteAuth(String authToken){
        allAuthData.remove(authToken);
    }
}
