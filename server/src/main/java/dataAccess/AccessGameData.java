package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AccessGameData {
    Map<Integer , GameData> allGameData;

    public AccessGameData() {
        this.allGameData = new HashMap<>();
    }
    public void clear() {
        this.allGameData.clear();
    }
    private void createGame(int gameID, String whiteUsername, String blackUsername, String gameName, ChessGame game){
        allGameData.put(gameID , new GameData(gameID, whiteUsername, blackUsername, gameName, game));
    }
    private GameData getGame(int gameID){
        return allGameData.get(gameID);
    }
    private Collection<GameData> listGames(){
        return allGameData.values();
    }
    private void updateGame(int gameID, ChessGame newGame){
        GameData chessGame = getGame(gameID);
        chessGame.game = newGame;
    }

}
