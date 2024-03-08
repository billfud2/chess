package service;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

import static dataAccess.DatabaseManager.createDatabase;

public class UserService {
    public DataAccess data = DataAccess.getInstance();

    public UserService() throws DataAccessException {
        createDatabase();
    }

    public AuthData register(UserData user) throws DataAccessException, BadRequestException, AlreadyTakenException {
        data.userDataAccess.createUser(user.username(), user.password(), user.email());
        return data.authDataAccess.createAuth(user.username());
    }
    public AuthData login(UserData user) throws DataAccessException {
        if (data.userDataAccess.getUser(user.username()).password().equals(user.password())){
            return data.authDataAccess.createAuth(user.username());
        }
        throw new DataAccessException("unauthorized");
    }
    public void logout(String authToken) throws DataAccessException {
        data.authDataAccess.deleteAuth(authToken);
    }
}
