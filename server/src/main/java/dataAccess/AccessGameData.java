package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class AccessGameData {
    Map<Integer , GameData> allGameData;
    int curID;

    public AccessGameData() {
        this.allGameData = new HashMap<>();
        this.curID = 0;
    }
    public void clear() {
        this.allGameData.clear();
    }
    public Integer createGame(String gameName) {
        int gameID = curID;
        curID++;
        allGameData.put(gameID, new GameData(gameID, null, null, gameName, new ChessGame()));
        return gameID;
    }
    private GameData getGame(int gameID){
        return allGameData.get(gameID);
    }
    public Collection<GameData> listGames(){
        return allGameData.values();
    }
    private void updateGame(int gameID, ChessGame newGame){
        GameData chessGame = getGame(gameID);
        chessGame.game = newGame;
    }

    public void addWhitePlayer(String username, int gameID) {
        allGameData.get(gameID).whiteUsername = username;
    }
    public void addBlackPlayer(String username, int gameID) {
        allGameData.get(gameID).blackUsername = username;
    }
}
