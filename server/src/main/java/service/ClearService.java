package service;

import dataAccess.DataAccess;
import model.UserData;

public class ClearService {
    public void ClearAll(DataAccess data){
        data.authDataAccess.clear();
        data.gameDataAccess.clear();
        data.userDataAccess.clear();
    }
}
