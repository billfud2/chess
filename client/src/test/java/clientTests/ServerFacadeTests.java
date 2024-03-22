package clientTests;

import dataAccess.AccessUserData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import facade.ServerFacade;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;


public class ServerFacadeTests {

    private String username = "billfud";
    private String password = "monkeyman";
    private String email = "billfud2@gmail.com";
    private static int desPort = 8080;

    private static Server server;
    private static ServerFacade facade = ServerFacade.getInstance("localhost", desPort);

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(desPort);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() throws InterruptedException {
        server.stop();
    }


    @Test
    public void testRegister() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, password, email));
            assert AccessUserData.getUser(username).email().equals(email);
        }catch(IOException e){
            assert false;
        }

    }
    @Test
    public void badTestRegister() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, null, email));
            assert false;
        }catch(IOException e){
            assert true;
        }

    }
    @Test
    public void testLogin() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, password, email));
            assert AccessUserData.getUser(username).email().equals(email);
        }catch(IOException e){
            assert false;
        }

    }
    @Test
    public void badTestLogin() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, null, email));
            assert false;
        }catch(IOException e){
            assert true;
        }

    }
}
