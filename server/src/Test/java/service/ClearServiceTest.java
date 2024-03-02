package service;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;

import java.util.Collection;

class ClearServiceTest {
    String username = "billfud";
    String password = "pass";
    String email = "billfud2@gmail.com";
    UserData uData = new UserData(username, password, email);
    String username2 = "billfud2";
    String password2 = "pass2";
    String email2 = "billfud22@gmail.com";
    UserData uData2 = new UserData(username2, password2, email2);
    DataAccess dB = new DataAccess();
    UserService serve = new UserService(dB);
    GameService gServe = new GameService(dB);
    ClearService cServe = new ClearService(dB);
    @Test
    void clearAll() throws DataAccessException {
        AuthData auth = serve.register(uData);
        CreateGameResult resID = gServe.createGame(new CreateGameRequest("best game", auth.authToken()));
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.WHITE, 0, auth.authToken()));
        ListGamesResult resGam = gServe.listGames(new AuthData(auth.authToken(),null));
        System.out.println(resGam.games());
        AuthData auth2 = serve.register(uData2);
        gServe.joinGame(new JoinGameRequest(ChessGame.TeamColor.BLACK, 0, auth2.authToken()));
        resGam = gServe.listGames(new AuthData(auth.authToken(),null));
        System.out.println(resGam.games());
        cServe.clearAll();
        assert cServe.data.gameDataAccess.allGameData.isEmpty();
        assert cServe.data.userDataAccess.allUserData.isEmpty();
        assert cServe.data.authDataAccess.allAuthData.isEmpty();
    }
}