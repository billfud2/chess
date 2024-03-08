package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;

import static dataAccess.DatabaseManager.createDatabase;

public class ClearService {



    public ClearService() throws DataAccessException {
        createDatabase();
    }

    public void clearAll(){

    }
}
