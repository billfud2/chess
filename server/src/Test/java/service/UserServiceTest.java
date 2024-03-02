package service;

import dataAccess.DataAccess;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;

class UserServiceTest {
    String username = "billfud";
    String password = "pass";
    String email = "billfud2@gmail.com";
    UserData uData = new UserData(username, password, email);
    UserService serve = new UserService(new DataAccess());

    @Test
    void register() {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        assert auth != null;
    }

    @Test
    void login() {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        AuthData authLog = serve.login(username, password);
        System.out.println("{authToken: " + authLog.authToken() + ", username: " + authLog.username() + '}');
        assert authLog != null;
    }

    @Test
    void logout() {
        AuthData auth = serve.register(uData);
        System.out.println("{authToken: " + auth.authToken() + ", username: " + auth.username() + '}');
        AuthData authLog = serve.login(username, password);
        System.out.println("{authToken: " + authLog.authToken() + ", username: " + authLog.username() + '}');
        serve.logout(authLog.authToken());
        assert serve.data.authDataAccess.allAuthData.get(authLog) == null;
    }
}