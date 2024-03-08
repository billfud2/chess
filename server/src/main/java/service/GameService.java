package service;

import chess.ChessGame;
import dataAccess.*;

import java.util.Collection;

import model.AuthData;
import model.GameData;
import requestAndResult.CreateGameRequest;
import requestAndResult.CreateGameResult;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;

import static dataAccess.DatabaseManager.createDatabase;

public class GameService {
    public GameService() throws DataAccessException {
        createDatabase();
    }

    public ListGamesResult listGames(String auth) throws DataAccessException {
        AccessAuthData.getAuth(auth);
        return new ListGamesResult(AccessGameData.listGames());
    }

    public CreateGameResult createGame(String gameName, String authToken) throws DataAccessException, BadRequestException {
        AccessAuthData.getAuth(authToken);
        return new CreateGameResult(AccessGameData.createGame(gameName));
    }

    public void joinGame(JoinGameRequest joinReq, String authToken) throws DataAccessException, BadRequestException, AlreadyTakenException {
        String username = AccessAuthData.getAuth(authToken).username();
        if (!data.gameDataAccess.allGameData.containsKey(joinReq.gameID())){
            throw new BadRequestException("bad request");
        }
        if (joinReq.playerColor() == ChessGame.TeamColor.WHITE){
            data.gameDataAccess.addWhitePlayer(username, joinReq.gameID());
        } else if (joinReq.playerColor() == ChessGame.TeamColor.BLACK) {
            data.gameDataAccess.addBlackPlayer(username, joinReq.gameID());
        }
    }
}
