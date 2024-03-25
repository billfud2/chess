package clientTests;

import chess.ChessGame;
import dataAccess.AccessAuthData;
import dataAccess.AccessGameData;
import dataAccess.AccessUserData;
import dataAccess.DataAccessException;
import model.UserData;
import org.junit.jupiter.api.*;
import recordsForReqAndRes.JoinGameRequest;
import server.Server;
import facade.ServerFacade;

import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.Objects;


public class ServerFacadeTests {

    private String username = "billfud";
    private String password = "monkeyman";
    private String email = "billfud2@gmail.com";
    private String gameName = "Best Game";

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = ServerFacade.getInstance("localhost", port);
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
            System.out.println(e.getMessage());
            assert true;
        }

    }
    @Test
    public void testLogin() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            assert facade.authLog != null;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert false;
        }

    }
    @Test
    public void badTestLogin() throws Exception {
        AccessUserData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.register(new UserData(username, "hi",null));
            assert false;
        }catch(IOException e){
            assert true;
        }

    }
    @Test
    public void testLogout() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            facade.logout();
            AccessAuthData.getAuth(facade.authLog);
            assert false;
        }catch(DataAccessException e){
            System.out.println(e.getMessage());
            assert true;
        }

    }
    @Test
    public void badTestLogout() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            facade.logout();
            facade.logout();
            assert false;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    public void testCreateGame() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            int id = facade.createGame(gameName);
            assert id == 1;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert false;
        }

    }
    @Test
    public void badCreateGame() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            int id = facade.createGame(gameName);
            assert false;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    public void testListGames() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            int id = facade.createGame(gameName);
            assert !facade.listGames().games().isEmpty();
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert false;
        }

    }
    @Test
    public void badListGames() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            int id = facade.createGame(gameName);
            facade.logout();
            facade.listGames().games();
            assert false;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert true;
        }
    }
    @Test
    public void testJoinGame() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            int id = facade.createGame(gameName);
            facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, id));
            String tempUsername = facade.listGames().games().get(0).whiteUsername();
            assert tempUsername.equals(username);
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert false;
        }

    }
    @Test
    public void badJoinGame() throws Exception {
        AccessUserData.clear();
        AccessAuthData.clear();
        AccessGameData.clear();
        try {
            facade.register(new UserData(username, password, email));
            facade.login(new UserData(username, password, null));
            int id = facade.createGame(gameName);
            facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, id));
            facade.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, id));
            assert false;
        }catch(IOException e){
            System.out.println(e.getMessage());
            assert true;
        }
    }
}
