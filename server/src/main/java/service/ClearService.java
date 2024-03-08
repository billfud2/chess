package service;

import dataAccess.*;

import static dataAccess.DatabaseManager.createDatabase;

public class ClearService {



    public ClearService() throws DataAccessException {
        createDatabase();
    }

    public void clearAll() throws Exception {
        AccessAuthData.clear();
        AccessGameData.clear();
        AccessUserData.clear();
    }
}
