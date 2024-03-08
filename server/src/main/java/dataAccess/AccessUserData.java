package dataAccess;


import model.UserData;

import java.sql.Connection;
import java.sql.SQLException;

public class AccessUserData {
    private static Connection conn;

    static {
        try {
            conn = DatabaseManager.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public AccessUserData() throws DataAccessException {    }
    static public void clear() throws Exception {
        try (var preparedStatement = conn.prepareStatement("TURNCATE TABLE user")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    static public void createUser(String username, String password, String email) throws DataAccessException, BadRequestException, AlreadyTakenException {
        if (getUser(username).username() != null) {
            throw new AlreadyTakenException("already taken");
        }
        if (username == null || password == null || email == null) {
            throw new BadRequestException("bad request");
        }
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public UserData getUser (String username) throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE type=?")) {
            preparedStatement.setString(1, username);
            var rs = preparedStatement.executeQuery();
            return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
        } catch (SQLException e) {
            throw new DataAccessException("unauthorized");
        }
    }
}
