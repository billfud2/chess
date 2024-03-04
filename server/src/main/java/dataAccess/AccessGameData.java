package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.*;

public class AccessGameData {
    public Map<Integer , GameData> allGameData;
    int curID;

    public AccessGameData() {
        this.allGameData = new HashMap<>();
        this.curID = 1;
    }
    public void clear() {
        this.allGameData.clear();
    }
    public Integer createGame(String gameName) throws BadRequestException {
        if (gameName != null) {
            int gameID = curID;
            curID++;
            allGameData.put(gameID, new GameData(gameID, null, null, gameName, new ChessGame()));
            return gameID;
        }
        throw new BadRequestException("bad request");
    }

    public Collection<GameData> listGames(){
        return allGameData.values();
    }

    public void addBlackPlayer(String username, int gameID) throws AlreadyTakenException {
        GameData gam = allGameData.get(gameID);
        if(allGameData.get(gameID).blackUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        allGameData.put(gameID, new GameData(gameID, gam.whiteUsername(), username, gam.gameName(), gam.game()));
    }
    public void addWhitePlayer(String username, int gameID) throws AlreadyTakenException, BadRequestException {
        GameData gam = allGameData.get(gameID);
        if(allGameData.get(gameID).whiteUsername() != null){
            throw new AlreadyTakenException("already taken");
        }
        allGameData.put(gameID, new GameData(gameID, username, gam.blackUsername(), gam.gameName(), gam.game()));
    }
}
