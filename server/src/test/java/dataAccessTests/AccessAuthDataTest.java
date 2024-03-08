package dataAccessTests;

import dataAccess.AccessAuthData;
import dataAccess.AccessUserData;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AccessAuthDataTest {
    UserData uData = new UserData("username","pass","email");
    private static Connection conn;
    static {
        try {
            conn = DatabaseManager.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void clear() {
        try {
            AccessAuthData.clear();
            AccessAuthData.createAuth(uData.username());
            AccessAuthData.clear();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM auth")) {
                var rs = preparedStatement.executeQuery();
                if(rs.next()){
                    assert false;
                }
                assert true;
            }
        }catch (Exception e){
            assert false;
        }
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
    void deleteAuth() throws Exception {
        try {
            AccessAuthData.clear();
            AuthData auth = AccessAuthData.createAuth(uData.username());
            AccessAuthData.deleteAuth(auth.authToken());
            AccessAuthData.getAuth(auth.authToken());
            assert false;
        }catch (DataAccessException e){
            assert true;
        }

    }
    @Test
    void badDeleteAuth() throws Exception {
        try {
            AccessAuthData.clear();
            AuthData auth = AccessAuthData.createAuth(uData.username());
            AccessAuthData.deleteAuth("hi");
            assert false;
        }catch (DataAccessException e){
            assert true;
        }

    }
}