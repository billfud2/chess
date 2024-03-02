package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class AccessGameData {
    public Map<Integer , GameData> allGameData;
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

    public Collection<GameData> listGames(){
        return allGameData.values();
    }
    private void updateGame(int gameID, GameData newGame){
        allGameData.put(gameID, newGame);


    }

    public void addBlackPlayer(String username, int gameID) {
        GameData gam = allGameData.get(gameID);
        allGameData.put(gameID, new GameData(gameID, gam.whiteUsername(), username, gam.gameName(), gam.game()));
    }
    public void addWhitePlayer(String username, int gameID) {
        GameData gam = allGameData.get(gameID);
        allGameData.put(gameID, new GameData(gameID, username, gam.blackUsername(), gam.gameName(), gam.game()));
    }
}
