package dataAccess;

public class DataAccess {
    public AccessGameData gameDataAccess;
    public AccessAuthData authDataAccess;
    public AccessUserData userDataAccess;
    public static DataAccess instance;
    private DataAccess() {
        this.gameDataAccess = new AccessGameData();
        this.authDataAccess = new AccessAuthData();
        this.userDataAccess = new AccessUserData();
    }

    public static DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }
}
