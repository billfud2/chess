package service;

import chess.ChessGame;
import dataAccess.DataAccess;

import java.util.Collection;

import dataAccess.DataAccessException;
import model.GameData;

public class GameService {
    DataAccess data;
    public GameService(DataAccess accessData) {data = accessData;
    }

    public Collection<GameData> listGames(String authToken) throws DataAccessException {
        data.authDataAccess.getAuth(authToken);
        return data.gameDataAccess.listGames();
    }

    public Integer createGame(String gameName, String authToken) throws DataAccessException {
        data.authDataAccess.getAuth(authToken);
        return data.gameDataAccess.createGame(gameName);
    }

    public void joinGame(ChessGame.TeamColor playerColor, int gameID, String authToken) throws DataAccessException {
        String username = data.authDataAccess.getAuth(authToken).username;
        if (playerColor == ChessGame.TeamColor.WHITE){
            data.gameDataAccess.addWhitePlayer(username, gameID);
        }else{
            data.gameDataAccess.addBlackPlayer(username, gameID);
        }
    }
}
