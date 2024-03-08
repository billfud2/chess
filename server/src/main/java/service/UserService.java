package service;

import dataAccess.*;
import model.AuthData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static dataAccess.DatabaseManager.createDatabase;

public class UserService {

    public UserService() throws DataAccessException {
        createDatabase();
    }

    public AuthData register(UserData user) throws DataAccessException, BadRequestException, AlreadyTakenException {
        AccessUserData.createUser(user.username(), user.password(), user.email());
        return AccessAuthData.createAuth(user.username());
    }
    public AuthData login(UserData user) throws DataAccessException {

        if (AccessUserData.getUser(user.username()) != null && AccessUserData.encoder.matches(user.password(), AccessUserData.getUser(user.username()).password())){
            return AccessAuthData.createAuth(user.username());
        }
        throw new DataAccessException("unauthorized");
    }
    public void logout(String authToken) throws DataAccessException {
        AccessAuthData.deleteAuth(authToken);
    }
}
