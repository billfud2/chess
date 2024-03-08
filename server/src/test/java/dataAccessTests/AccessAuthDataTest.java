package dataAccessTests;

import dataAccess.AccessAuthData;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessAuthDataTest {
    UserData uData = new UserData("username","pass","email");

    @Test
    void clear() {
    }

    @Test
    void createAuth() throws Exception {
        AccessAuthData.clear();
        AuthData auth = AccessAuthData.createAuth(uData.username());
        assert AccessAuthData.getAuth(auth.authToken()).equals(auth);
    }
    @Test
    void badCreateAuth() throws Exception {
        try {
            AccessAuthData.clear();
            AuthData auth = AccessAuthData.createAuth("un");
            assert false;
        }catch(DataAccessException e){
            assert true;
        }catch (Exception e){
            assert false;
        }
    }
    @Test
    void getAuth() throws Exception {
        AccessAuthData.clear();
        AuthData auth = AccessAuthData.createAuth(uData.username());
        assert AccessAuthData.getAuth(auth.authToken()).equals(auth);
    }

    @Test
    void badGetAuth() throws Exception {
        try {
            AccessAuthData.clear();
            AuthData auth = AccessAuthData.createAuth(uData.username());
            AccessAuthData.getAuth("bad");
            assert false;
        }catch (DataAccessException e){
            assert true;
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    void deleteAuth() {
    }
}