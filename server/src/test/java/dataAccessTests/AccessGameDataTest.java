package dataAccessTests;

import chess.ChessGame;
import dataAccess.AccessGameData;
import dataAccess.BadRequestException;
import dataAccess.DataAccessException;
import dataAccess.DatabaseManager;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AccessGameDataTest {
    UserData uData = new UserData("username","pass","email");
    GameData gData = new GameData(1, "white", "black", "gam", new ChessGame());
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
    void listGames() {
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
    void addBlackPlayer() {
    }

    @Test
    void addWhitePlayer() {
    }
}