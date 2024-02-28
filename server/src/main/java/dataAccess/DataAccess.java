package dataAccess;

import model.AuthData;

public class DataAccess {
    public AccessGameData gameDataAccess;
    public AccessAuthData authDataAccess;
    public AccessUserData userDataAccess;

    public DataAccess(AccessGameData gameDataAccess, AccessAuthData authDataAccess, AccessUserData userDataAccess) {
        this.gameDataAccess = gameDataAccess;
        this.authDataAccess = authDataAccess;
        this.userDataAccess = userDataAccess;
    }
}
