package service;

import dataAccess.DataAccess;

public class ClearService {
    DataAccess data;

    public ClearService(DataAccess accessData) {
        data = accessData;
    }

    public void clearAll(){
        data.authDataAccess.clear();
        data.gameDataAccess.clear();
        data.userDataAccess.clear();
    }
}
