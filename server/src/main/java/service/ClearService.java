package service;

import dataAccess.DataAccess;
import model.UserData;

public class ClearService {
    DataAccess data;

    public ClearService(DataAccess accessData) {
        data = accessData;
    }

    public void ClearAll(){
        data.authDataAccess.clear();
        data.gameDataAccess.clear();
        data.userDataAccess.clear();
    }
}