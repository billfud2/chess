package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessAuthData {
    private static Connection conn;

    static {
        try {
            conn = DatabaseManager.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AccessAuthData() throws DataAccessException {
    }
    static public void clear() throws Exception {
        try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE auth")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
    static public AuthData createAuth(String username) throws DataAccessException {
        String authToken =  UUID.randomUUID().toString();
        if(AccessUserData.getUser(username) == null){
            throw new DataAccessException("unauthorized");
        }
        try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (username, authToken) VALUES(?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, authToken);
            preparedStatement.executeUpdate();
            return new AuthData(authToken, username);
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public AuthData getAuth(String authToken) throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("SELECT username, authToken FROM auth WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            var rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new AuthData(authToken ,rs.getString("username"));
            }else{
                throw new DataAccessException("unauthorized");
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public void deleteAuth(String authToken) throws DataAccessException {
        if(getAuth(authToken).authToken() == null){
            throw new DataAccessException("unauthorized");
        }
        try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE authToken=?")) {
            preparedStatement.setString(1, authToken);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
