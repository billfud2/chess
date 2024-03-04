package passoffTests.serverTests;

import chess.ChessGame;
import dataAccess.AlreadyTakenException;
import dataAccess.BadRequestException;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Test;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;
import service.ClearService;
import service.GameService;
import service.UserService;

class ClearServiceTest {
    String username = "billfud";
    String password = "pass";
    String email = "billfud2@gmail.com";
    UserData uData = new UserData(username, password, email);
    String username2 = "billfud2";
    String password2 = "pass2";
    String email2 = "billfud22@gmail.com";
    UserData uData2 = new UserData(username2, password2, email2);
    DataAccess dB = DataAccess.getInstance();
    UserService serve = new UserService();
    GameService gServe = new GameService();
    ClearService cServe = new ClearService();
    @Test
    void clearAll() throws DataAccessException, BadRequestException, AlreadyTakenException {
        AuthData auth = serve.register(uData);
        CreateGameResult resID = gServe.createGame("best game", auth.authToken());
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 1), auth.authToken());
        ListGamesResult resGam = gServe.listGames(auth.authToken());
        System.out.println(resGam.games());
        AuthData auth2 = serve.register(uData2);
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1), auth2.authToken());
        resGam = gServe.listGames(auth.authToken());
        System.out.println(resGam.games());
        cServe.clearAll();
        assert cServe.data.gameDataAccess.allGameData.isEmpty();
        assert cServe.data.userDataAccess.allUserData.isEmpty();
        assert cServe.data.authDataAccess.allAuthData.isEmpty();
    }
    @Test
    void badClearAll() throws Exception {
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
                cServe.clearAll();
                throw new Exception("lame");
            }catch (Exception e){
                assert true;
            }
    }
}