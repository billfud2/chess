package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class AccessGameDataTest {
    UserData uData = new UserData("username","pass","email");
    GameData gData = new GameData(0, "white", "black", "gam", new ChessGame());
    private static Connection conn;
    static {
        try {
            conn = DatabaseManager.getConnection();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void clear() throws Exception {
        try{
            AccessGameData.clear();
            AccessGameData.createGame(gData.gameName());
            AccessGameData.clear();
            try (var preparedStatement = conn.prepareStatement("SELECT * FROM game")) {
                var rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    assert false;
                }
                assert true;
            }
        }catch (Exception e){
            assert false;
        }
    }

    @Test
    void createGame() throws Exception {
        AccessGameData.clear();
        int gameID = AccessGameData.createGame(gData.gameName());
        assert AccessGameData.getGame(gameID) != null;
    }
    @Test
    void badCreateGame() throws Exception {
        try {
            AccessGameData.clear();
            int gameID = AccessGameData.createGame(null);
            assert false;
        }catch(BadRequestException e){
            assert true;
        }catch(Exception e){
            assert false;
        }
    }

    @Test
    void listGames() throws Exception {
        AccessGameData.clear();
        AccessUserData.clear();
        AccessUserData.createUser(uData.username(), uData.password(), uData.email());
        String auth = AccessAuthData.createAuth(uData.username()).authToken();
        int gameID = AccessGameData.createGame(gData.gameName());
        Collection<GameData> games = AccessGameData.listGames(auth);
        assert games.size() == 1;
    }
    @Test
    void badListGames() throws Exception {
        try {
            AccessGameData.clear();
            String auth = AccessAuthData.createAuth(uData.username()).authToken();
            int gameID = AccessGameData.createGame(gData.gameName());
            Collection<GameData> games = AccessGameData.listGames("auth");
            assert false;
        }catch(DataAccessException e){
            assert true;
        }
    }

    @Test
    void getGame() throws Exception {
        AccessGameData.clear();
        int gameID = AccessGameData.createGame(gData.gameName());
        assert AccessGameData.getGame(gameID) != null;
    }
    @Test
    void badGetGame() throws Exception {
        AccessGameData.clear();
        int gameID = AccessGameData.createGame(gData.gameName());
        assert AccessGameData.getGame(0) == null;
    }

    @Test
    void addBlackPlayer() throws Exception {
        AccessGameData.clear();
        int gameID = AccessGameData.createGame(gData.gameName());
        AccessGameData.addBlackPlayer(gData.blackUsername(), gameID);
        assert AccessGameData.getGame(gameID).blackUsername().equals(gData.blackUsername());
    }
    @Test
    void badAddBlackPlayer() throws Exception {
        try {
            AccessGameData.clear();
            int gameID = AccessGameData.createGame(gData.gameName());
            AccessGameData.addBlackPlayer(null, 0);
            assert false;
        }catch (BadRequestException e){
            assert true;
        }
    }

    @Test
    void addWhitePlayer() throws Exception {
        AccessGameData.clear();
        int gameID = AccessGameData.createGame(gData.gameName());
        AccessGameData.addWhitePlayer(gData.whiteUsername(), gameID);
        assert AccessGameData.getGame(gameID).whiteUsername().equals(gData.whiteUsername());
    }
    @Test
    void badAddWhitePlayer() throws Exception {
        try {
            AccessGameData.clear();
            int gameID = AccessGameData.createGame(gData.gameName());
            AccessGameData.addWhitePlayer(null, 0);
            assert false;
        }catch (BadRequestException e){
            assert true;
        }
    }
}