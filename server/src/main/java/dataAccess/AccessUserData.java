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
    public void createUser(String username, String password, String email) throws DataAccessException, BadRequestException, AlreadyTakenException {
        if (allUserData.containsKey(username)){
            throw new AlreadyTakenException("already taken");
        }
        if(username == null || password == null || email == null){
            throw new BadRequestException("bad request");
        }
        allUserData.put(username, new UserData(username, password, email));
    }
    public UserData getUser(String username) throws DataAccessException {
        if(allUserData.containsKey(username)) {
            return allUserData.get(username);
        }
        throw new DataAccessException("unauthorized");
    }
}
