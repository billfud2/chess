package serverTests;

import chess.ChessGame;
import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;
import service.ClearService;
import service.GameService;
import service.UserService;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServiceTest {
    String username = "billfud";
    String password = "pass";
    String email = "billfud2@gmail.com";
    UserData uData = new UserData(username, password, email);
    String username2 = "billfud2";
    String password2 = "pass2";
    String email2 = "billfud22@gmail.com";
    UserData uData2 = new UserData(username2, password2, email2);
    String username3 = "billfud3";
    String password3 = "pass3";
    String email3 = "billfud3@gmail.com";
    UserData uData3 = new UserData(username3, password3, email3);
    DataAccess dB = DataAccess.getInstance();
    UserService serve = new UserService();
    GameService gServe = new GameService();
    ClearService cServe = new ClearService();
    @BeforeEach
    public void setup(){
        cServe.clearAll();
    }
    @Test
    void createGame() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        CreateGameResult resID = gServe.createGame("best game", auth.authToken());
        assert resID.gameID() >= 1;
    }
    @Test
    void badCreateGame() throws DataAccessException, BadRequestException, AlreadyTakenException {
        try {
            AuthData auth = serve.register(uData);
            CreateGameResult resID = gServe.createGame("best game", "hi");
            assert false;
        }catch(Exception e){
            assertTrue(e instanceof DataAccessException);
        }
    }


    @Test
    void listGames() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        CreateGameResult resID = gServe.createGame("best game", auth.authToken());
        ListGamesResult resGam = gServe.listGames(auth.authToken());
        System.out.println(resGam.games());
        assert !resGam.games().isEmpty();
    }
    @Test
    void badListGames() throws DataAccessException, BadRequestException, AlreadyTakenException {
        try {
            AuthData auth = serve.register(uData);
            CreateGameResult resID = gServe.createGame("best game", auth.authToken());
            ListGamesResult resGam = gServe.listGames("hi");
            System.out.println(resGam.games());
            assert false;
        }catch(Exception e){
            assertTrue(e instanceof DataAccessException);
        }
    }




    @Test
    void joinGame() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        CreateGameResult resID = gServe.createGame("best game", auth.authToken());
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 1),auth.authToken());
        ListGamesResult resGam = gServe.listGames(auth.authToken());
        System.out.println(resGam.games());
        AuthData auth2 = serve.register(uData2);
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), auth2.authToken());
        resGam = gServe.listGames(auth.authToken());
        System.out.println(resGam.games());
        assert dB.gameDataAccess.allGameData.get(resID.gameID()).whiteUsername() == username;
        assert dB.gameDataAccess.allGameData.get(resID.gameID()).blackUsername() == username2;
    }
    @Test
    void badJoinGame() throws DataAccessException, BadRequestException, AlreadyTakenException {
        try {
            AuthData auth = serve.register(uData);
            CreateGameResult resID = gServe.createGame("best game", auth.authToken());
            gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 1), auth.authToken());
            ListGamesResult resGam = gServe.listGames(auth.authToken());
            System.out.println(resGam.games());
            AuthData auth2 = serve.register(uData2);
            gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), auth2.authToken());
            resGam = gServe.listGames(auth.authToken());
            System.out.println(resGam.games());
            AuthData auth3 = serve.register(uData3);
            gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), auth3.authToken());
            assert false;
        }catch(Exception e){
            assert true;
        }

    }
}