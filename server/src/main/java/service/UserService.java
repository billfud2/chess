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
        data.userDataAccess.createUser(user.username(), user.password(), user.email());
        return data.authDataAccess.createAuth(user.username());
    }
    public AuthData login(UserData user) {
        if (data.userDataAccess.getUser(user.username()).password() == user.password()){
            return data.authDataAccess.createAuth(user.username());
        }
        throw new RuntimeException("invalid Login");
    }
    public void logout(AuthData auth) {
        data.authDataAccess.deleteAuth(auth.authToken());
    }
}
