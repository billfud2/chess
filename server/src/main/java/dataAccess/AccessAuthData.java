package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessAuthData {


    public AccessAuthData() {
    }
    static public void clear() {

    }
    static public AuthData createAuth(String username){
        String authToken =  UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        return auth;
    }
    static public AuthData getAuth(String authToken) throws DataAccessException {
        if(allAuthData.containsKey(authToken)){
            return allAuthData.get(authToken);
        }
        else{
            throw new DataAccessException("Error: unauthorized");
        }
    }
    static public void deleteAuth(String authToken) throws DataAccessException {
        if (allAuthData.containsKey(authToken)) {
            allAuthData.remove(authToken);
        }else {
            throw new DataAccessException("unauthorized");
        }
    }
}
