package service;

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