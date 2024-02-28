package dataAccess;

import model.AuthData;
import model.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessAuthData {
    Map<String, AuthData> allAuthData;

    public AccessAuthData() {
        this.allAuthData = new HashMap<>();
    }
    public void clear() {
        this.allAuthData.clear();
    }
    public AuthData createAuth(String username){
        String authToken =  UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        allAuthData.put(authToken, auth);
        return auth;
    }
    public AuthData getAuth(String authToken){
        return allAuthData.get(authToken);
    }
    public void deleteAuth(String authToken){
        allAuthData.remove(authToken);
    }
}
