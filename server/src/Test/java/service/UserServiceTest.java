package service;

import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceTest {
    String username = "billfud";
    String password = "pass";
    String email = "billfud2@gmail.com";
    UserData uData = new UserData(username, password, email);
    UserService serve = new UserService();

    @Test
    void register() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        assert auth != null;
    }
    @Test
    void badRegister() throws DataAccessException, BadRequestException, AlreadyTakenException {

        try {
            // Call the route handler that should throw an exception
            AuthData auth = serve.register(uData);
            AuthData auth2 = serve.register(uData);

        } catch (Exception e) {
            // Verify that the correct type of exception is thrown
            assertTrue(e instanceof AlreadyTakenException);
        }
    }

    @Test
    void login() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        AuthData authLog = serve.login(new UserData(username, password,null));
        System.out.println("{authToken: " + authLog.authToken() + ", username: " + authLog.username() + '}');
        assert authLog != null;
    }

    @Test
    void logout() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        AuthData authLog = serve.login(new UserData(username, password,null));
        System.out.println("{authToken: " + authLog.authToken() + ", username: " + authLog.username() + '}');
        serve.logout(authLog);
        assert serve.data.authDataAccess.allAuthData.get(authLog) == null;
    }
}