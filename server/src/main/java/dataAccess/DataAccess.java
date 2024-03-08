package dataAccess;

public class DataAccess {
    public AccessGameData gameDataAccess;
    public AccessAuthData authDataAccess;
    public AccessUserData userDataAccess;
    public static DataAccess instance;
    private DataAccess() {

    }

    public static DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }
}
