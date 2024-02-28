package service;

import dataAccess.DataAccess;
import model.AuthData;
import model.UserData;

public class UserService {
    DataAccess data;

    public UserService(DataAccess accessData) {
        data = accessData;
    }

    public AuthData register(UserData user) {
        data.userDataAccess.createUser(user.username, user.password, user.email);
        return data.authDataAccess.createAuth(user.username);
    }
    public AuthData login(String username,String password ) {
        if (data.userDataAccess.getUser(username).password == password){
            return data.authDataAccess.createAuth(username);
        }
        throw new RuntimeException("invalid Login");
    }
    public void logout(String authToken) {
        data.authDataAccess.deleteAuth(authToken);
    }
}
