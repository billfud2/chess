package dataAccessTests;

import dataAccess.*;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;



class AccessUserDataTest {
    UserData uData = new UserData("username","pass","email");
    private static Connection conn;

    static {
        try {
            DatabaseManager.createDatabase();
            conn = DatabaseManager.getConnection();

        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void clear() {
        try {
            AccessUserData.clear();
            AccessUserData.createUser(uData.username(), uData.password(), uData.email());
            AccessUserData.clear();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM user")) {
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
    void createUser() throws Exception {
        AccessUserData.clear();
        AccessUserData.createUser(uData.username(), uData.password(), uData.email());
        assert AccessUserData.getUser(uData.username()).username().equals(uData.username());
    }
    @Test
    void badCreateUser() throws Exception {
        try {
            AccessUserData.clear();
            AccessUserData.createUser(uData.username(), uData.password(), uData.email());
            AccessUserData.createUser(uData.username(), uData.password(), uData.email());
            assert false;
        }catch (AlreadyTakenException e){
            assert true;
        }catch(Exception e){
            assert false;
        }

    }

    @Test
    void getUser() throws Exception {
        AccessUserData.clear();
        AccessUserData.createUser(uData.username(), uData.password(), uData.email());
        assert AccessUserData.getUser(uData.username()).username().equals(uData.username());
    }
    @Test
    void badGetUser() throws Exception {
        AccessUserData.clear();
        AccessUserData.createUser(uData.username(), uData.password(), uData.email());
        assert AccessUserData.getUser("ug") == null;
    }
}