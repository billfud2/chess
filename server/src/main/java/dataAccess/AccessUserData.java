package dataAccess;


import model.UserData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.SQLException;

public class AccessUserData {
    public static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
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
        try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE user")) {
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }

    static public void createUser(String username, String password, String email) throws DataAccessException, BadRequestException, AlreadyTakenException {
        if (getUser(username) != null) {
            throw new AlreadyTakenException("already taken");
        }
        if (username == null || password == null || email == null) {
            throw new BadRequestException("bad request");
        }
        String hashedPassword = encoder.encode(password);
        try (var preparedStatement = conn.prepareStatement("INSERT INTO user (username, password, email) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    static public UserData getUser (String username) throws DataAccessException {
        try (var preparedStatement = conn.prepareStatement("SELECT username, password, email FROM user WHERE username=?")) {
            preparedStatement.setString(1, username);
            var rs = preparedStatement.executeQuery();
            if(rs.next()) {
                return new UserData(rs.getString("username"), rs.getString("password"), rs.getString("email"));
            }else{
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
