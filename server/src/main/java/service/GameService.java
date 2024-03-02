package service;

import chess.ChessGame;
import dataAccess.DataAccess;

import java.util.Collection;

import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import requestAndResult.CreateGameRequest;
import requestAndResult.JoinGameRequest;
import requestAndResult.ListGamesResult;

public class GameService {
    DataAccess data;
    public GameService(DataAccess accessData) {data = accessData;
    }

    public ListGamesResult listGames(AuthData auth) throws DataAccessException {
        data.authDataAccess.getAuth(auth.authToken());
        return new ListGamesResult(data.gameDataAccess.listGames());
    }

    public Integer createGame(CreateGameRequest gameReq) throws DataAccessException {
        data.authDataAccess.getAuth(gameReq.authToken());
        return data.gameDataAccess.createGame(gameReq.gameName());
    }

    public void joinGame(JoinGameRequest joinReq) throws DataAccessException {
        String username = data.authDataAccess.getAuth(joinReq.authToken()).username();
        if (joinReq.playerColor() == ChessGame.TeamColor.WHITE){
            data.gameDataAccess.addWhitePlayer(username, joinReq.gameID());
        }else{
            data.gameDataAccess.addBlackPlayer(username, joinReq.gameID());
        }
    }
}
