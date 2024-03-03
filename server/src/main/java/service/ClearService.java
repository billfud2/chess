package service;

import dataAccess.DataAccess;

public class ClearService {
    DataAccess data = DataAccess.getInstance();

    public ClearService() {}

    public void clearAll(){
        data.authDataAccess.clear();
        data.gameDataAccess.clear();
        data.userDataAccess.clear();
    }
}
