package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessAuthData {
    public Map<String, AuthData> allAuthData;

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
    public AuthData getAuth(String authToken) throws DataAccessException {
        if(allAuthData.containsKey(authToken)){
            return allAuthData.get(authToken);
        }
        else{
            throw new DataAccessException("Error: unauthorized");
        }
    }
    public void deleteAuth(String authToken) throws DataAccessException {
        if (allAuthData.containsKey(authToken)) {
            allAuthData.remove(authToken);
        }else {
            throw new DataAccessException("unauthorized");
        }
    }
}
