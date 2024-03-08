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
        return new ListGamesResult(AccessGameData.listGames(auth));
    }

    public CreateGameResult createGame(String gameName, String authToken) throws DataAccessException, BadRequestException {
        AccessAuthData.getAuth(authToken);
        return new CreateGameResult(AccessGameData.createGame(gameName));
    }

    public void joinGame(JoinGameRequest joinReq, String authToken) throws DataAccessException, BadRequestException, AlreadyTakenException {
        String username = AccessAuthData.getAuth(authToken).username();
        if (AccessGameData.getGame(joinReq.gameID()) == null){
            throw new BadRequestException("bad request");
        }
        if (joinReq.playerColor() == ChessGame.TeamColor.WHITE){
            AccessGameData.addWhitePlayer(username, joinReq.gameID());
        } else if (joinReq.playerColor() == ChessGame.TeamColor.BLACK) {
            AccessGameData.addBlackPlayer(username, joinReq.gameID());
        }
    }
}
