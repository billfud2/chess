package dataAccess;

public class DataAccess {
    public AccessGameData gameDataAccess;
    public AccessAuthData authDataAccess;
    public AccessUserData userDataAccess;
    public DataAccess() {
        this.gameDataAccess = new AccessGameData();
        this.authDataAccess = new AccessAuthData();
        this.userDataAccess = new AccessUserData();
    }
}
